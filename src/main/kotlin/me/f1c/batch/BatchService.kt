package me.f1c.batch

import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.util.DateTimeUtil.SERVER_TIME_ZONE
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class BatchService(
    private val asyncJobLauncher: JobLauncher,
    private val context: ApplicationContext,
) {
    companion object {
        private const val TIMESTAMP = "timestamp"
    }

    fun executeJob(jobLaunchRequest: JobLaunchRequest): JobExecutionStatus {
        val (jobName, parameters) = jobLaunchRequest
        return try {
            val now =
                Clock.System
                    .now()
                    .toLocalDateTime(SERVER_TIME_ZONE)
                    .toInstant(SERVER_TIME_ZONE)
                    .epochSeconds

            val job = context.getBean(jobName, Job::class.java)
            val jobParameters =
                JobParametersBuilder()
                    .apply {
                        parameters.forEach { (key, value) ->
                            addString(key, value)
                        }
                        addLong(TIMESTAMP, now)
                    }.toJobParameters()

            val jobExecution = asyncJobLauncher.run(job, jobParameters)

            if (jobExecution.status == BatchStatus.STARTING) {
                LOGGER.info("[{}] executeJob: {}", LogResult.SUCCEEDED, jobName)
            } else {
                LOGGER.warn("[{}] executeJob: {}, {}", LogResult.FAILED, jobName, jobExecution.status)
            }

            JobExecutionStatus(
                jobName = jobName,
                jobExecutionId = jobExecution.id,
                batchStatus = jobExecution.status,
                exitStatus = jobExecution.exitStatus,
                parameters = parameters,
            )
        } catch (e: Exception) {
            LOGGER.warn("[{}] executeJob: {}, {}, {}", LogResult.FAILED, jobName, parameters, e.message, e)
            JobExecutionStatus(
                jobName = jobName,
                jobExecutionId = -1,
                batchStatus = BatchStatus.FAILED,
                exitStatus = ExitStatus.FAILED,
                parameters = parameters,
            )
        }
    }
}
