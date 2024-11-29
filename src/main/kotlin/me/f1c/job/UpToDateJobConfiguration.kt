package me.f1c.job

import me.f1c.configuration.DatabaseConfiguration.Companion.BATCH_DATA_SOURCE
import me.f1c.configuration.DatabaseConfiguration.Companion.BATCH_TRANSACTION_MANAGER
import me.f1c.domain.batch.BatchService
import me.f1c.domain.batch.JobLaunchRequest
import me.f1c.job.tasklet.UpToDateTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing(dataSourceRef = BATCH_DATA_SOURCE, transactionManagerRef = BATCH_TRANSACTION_MANAGER)
class UpToDateJobConfiguration(
    private val batchService: BatchService,
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val upToDateTasklet: UpToDateTasklet,
) {
    companion object {
        private const val JOB_NAME = "upToDateJob"
        private const val STEP_NAME = "upToDateStep"
    }

    @Bean
    fun upToDateJob(): Job =
        JobBuilder(JOB_NAME, jobRepository)
            .start(upToDateStep())
            .build()

    @Bean
    fun upToDateStep(): Step =
        StepBuilder(STEP_NAME, jobRepository)
            .tasklet(upToDateTasklet, transactionManager)
            .build()

    @Scheduled(cron = "0 0 0 * * *")
    fun scheduleUpToDateJob() {
        batchService.executeJob(JobLaunchRequest(jobName = JOB_NAME))
    }
}
