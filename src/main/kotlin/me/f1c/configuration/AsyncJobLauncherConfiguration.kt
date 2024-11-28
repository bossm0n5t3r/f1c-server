package me.f1c.configuration

import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class AsyncJobLauncherConfiguration {
    @Bean
    @Throws(Exception::class)
    fun asyncJobLauncher(jobRepository: JobRepository): JobLauncher =
        TaskExecutorJobLauncher().apply {
            this.setJobRepository(jobRepository)
            this.setTaskExecutor(batchTaskExecutor())
            this.afterPropertiesSet()
        }

    @Bean
    fun batchTaskExecutor(): TaskExecutor =
        ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 1
            this.maxPoolSize = 1
            this.queueCapacity = 20
            this.setThreadNamePrefix("BATCH-THREAD-")
            this.initialize()
        }
}
