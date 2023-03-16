package io.archimedesfw.security.auth.micronaut

import io.archimedesfw.security.auth.UsernamePrincipal
import io.archimedesfw.security.auth.token.refresh.RefreshTokenAuthenticator
import io.archimedesfw.security.auth.token.refresh.RefreshTokenCredential
import io.archimedesfw.security.auth.token.refresh.RefreshTokenService
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import io.micronaut.transaction.SynchronousTransactionManager
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import java.sql.Connection

@Singleton
internal open class DefaultRefreshTokenPersistence(
    private val refreshTokenService: RefreshTokenService,
    private val refreshTokenAuthenticator: RefreshTokenAuthenticator,
    private val tx: SynchronousTransactionManager<Connection>,
    @Named(TaskExecutors.IO) private val ioScheduler: Scheduler,
) : RefreshTokenPersistence {

    private val log = LoggerFactory.getLogger(DefaultRefreshTokenPersistence::class.java)

    @EventListener
    override fun persistToken(event: RefreshTokenGeneratedEvent) {
        tx.executeWrite {
            refreshTokenService.reset(
                UsernamePrincipal(event.authentication.name),
                RefreshTokenCredential(event.refreshToken)
            )
        }
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> =
        Mono.create { emitter ->
            try {
                val subject = tx.executeRead {
                    refreshTokenAuthenticator.authenticate(RefreshTokenCredential(refreshToken))
                }

                val authentication = Authentication.build(
                    subject.principal.name,
                    subject.roles.map { it.name },
                    subject.attributes
                )
                emitter.success(authentication)

            } catch (ex: OauthErrorResponseException) {
                emitter.error(ex)
            } catch (th: Throwable) {
                log.error("Cannot authenticate with refresh token", th)
                emitter.error(OauthErrorResponseException(INVALID_GRANT))
            }
        }.subscribeOn(ioScheduler)

}
