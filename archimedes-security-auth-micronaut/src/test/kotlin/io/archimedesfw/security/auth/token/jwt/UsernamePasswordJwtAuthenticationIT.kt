package io.archimedesfw.security.auth.token.jwt

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.archimedesfw.security.auth.Role
import io.archimedesfw.security.auth.Subject
import io.archimedesfw.security.auth.UsernamePrincipal
import io.archimedesfw.security.auth.password.PasswordCredential
import io.archimedesfw.security.auth.password.UsernamePasswordAuthenticator
import io.archimedesfw.security.auth.token.refresh.RefreshTokenAuthenticator
import io.archimedesfw.security.auth.token.refresh.RefreshTokenCredential
import io.archimedesfw.security.auth.token.refresh.RefreshTokenService
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.HttpStatus.UNAUTHORIZED
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.cookie.Cookie
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.*

@MicronautTest
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
internal class UsernamePasswordJwtAuthenticationIT {

    @get:MockBean(UsernamePasswordAuthenticator::class)
    internal val usernamePasswordAuthenticator = mock<UsernamePasswordAuthenticator>()

    @get:MockBean(RefreshTokenService::class)
    internal val refreshTokenService = mock<RefreshTokenService>()

    @get:MockBean(RefreshTokenAuthenticator::class)
    internal val refreshTokenAuthenticator = mock<RefreshTokenAuthenticator>()

    @Inject
    @field:Client("/")
    private lateinit var client: HttpClient

    private lateinit var jwtCookie: Cookie
    private lateinit var jwtRefreshCookie: Cookie

    private lateinit var refreshToken: RefreshTokenCredential

    @Test
    @Order(10)
    fun `accessing a secured URL without authenticating returns unauthorized`() {
        val ex = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange<Any, Any>(GET("/hello"))
        }

        assertEquals(UNAUTHORIZED, ex.status)
        verifyNoInteractions(usernamePasswordAuthenticator, refreshTokenAuthenticator)
    }

    @Test
    @Order(20)
    fun `authenticate and get JWT and refresh token from cookie, without redirection`() {
        doReturn(SUBJECT).whenever(usernamePasswordAuthenticator).authenticate(PRINCIPAL, CREDENTIAL)

        val response = client.toBlocking().exchange<Any, Any>(
            POST(
                "/login",
                mapOf(
                    "username" to PRINCIPAL.name,
                    "password" to CREDENTIAL.secret
                )
            )
        )

        assertEquals(OK, response.status)

        jwtCookie = response.getCookie("JWT").get()
        assertCookie(jwtCookie, "JWT", "/", 3600)

        jwtRefreshCookie = response.getCookie("JWT_REFRESH_TOKEN").get()
        assertCookie(jwtRefreshCookie, "JWT_REFRESH_TOKEN", "/oauth/access_token", 2592000)

        val captorPrincipalName = argumentCaptor<UsernamePrincipal>()
        val captorRefreshToken = argumentCaptor<RefreshTokenCredential>()
        verify(refreshTokenService).reset(captorPrincipalName.capture(), captorRefreshToken.capture())

        refreshToken = captorRefreshToken.firstValue

        assertTrue(refreshToken.secret.isNotBlank())
        assertEquals("john", captorPrincipalName.firstValue.name)
    }

    @Test
    @Order(30)
    fun `use JWT cookie to access secure URL`() {
        reset(usernamePasswordAuthenticator, refreshTokenAuthenticator)

        val response = client.toBlocking().exchange(GET<Any>("/hello").cookie(jwtCookie), Map::class.java)

        assertEquals(OK, response.status)
        assertEquals(mapOf("principal" to "john"), response.body())
        verifyNoInteractions(usernamePasswordAuthenticator, refreshTokenAuthenticator)
    }

    @Test
    @Order(40)
    fun `use refresh token to get new JWT`() {
        doReturn(SUBJECT).whenever(refreshTokenAuthenticator).authenticate(refreshToken)

        val response = client.toBlocking().exchange(
            POST("/oauth/access_token", null).contentType("application/json").cookie(jwtRefreshCookie),
            Map::class.java
        )

        assertEquals(OK, response.status)

        val newJwtCookie = response.getCookie("JWT").get()
        assertCookie(newJwtCookie, "JWT", "/", 3600)

        val newJwtRefreshCookie = response.getCookie("JWT_REFRESH_TOKEN").get()
        assertCookie(newJwtRefreshCookie, "JWT_REFRESH_TOKEN", "/oauth/access_token", 2592000)

        verifyNoInteractions(usernamePasswordAuthenticator)
    }

    private companion object {
        private val PRINCIPAL = UsernamePrincipal("john")
        private val CREDENTIAL = PasswordCredential("secret")
        private val SUBJECT = Subject(
            1,
            PRINCIPAL,
            setOf(Role("ROLE_1")),
            mapOf("foobar" to 1)
        )

        private fun assertCookie(cookie: Cookie, name: String, path: String, maxAge: Long) {
            assertEquals(name, cookie.name)
            assertEquals("auth.com", cookie.domain)
            assertEquals(path, cookie.path)
            assertEquals(true, cookie.isHttpOnly)
            assertEquals(false, cookie.isSecure) // Because connection is not HTTPS
            assertEquals(maxAge, cookie.maxAge)
            assertTrue(cookie.sameSite.isEmpty)
            assertNotNull(cookie.value)
            assertTrue(JWTParser.parse(cookie.value) is SignedJWT)
        }
    }
}
