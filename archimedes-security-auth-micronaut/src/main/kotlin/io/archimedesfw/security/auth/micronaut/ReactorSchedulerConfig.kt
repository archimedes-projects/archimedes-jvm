package io.archimedesfw.security.auth.micronaut

import io.micronaut.context.annotation.Factory
import io.micronaut.scheduling.TaskExecutors
import jakarta.inject.Named
import jakarta.inject.Singleton
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ExecutorService

@Factory
internal class ReactorSchedulerConfig {

    @Singleton
    @Named(TaskExecutors.IO)
    internal fun ioScheduler(@Named(TaskExecutors.IO) executorService: ExecutorService): Scheduler =
        Schedulers.fromExecutorService(executorService)

}
