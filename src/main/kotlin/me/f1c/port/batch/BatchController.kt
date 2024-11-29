package me.f1c.port.batch

import me.f1c.domain.ResponseDto
import me.f1c.domain.batch.JobExecutionStatus
import me.f1c.domain.batch.JobLaunchRequest

interface BatchController {
    fun executeJob(jobLaunchRequest: JobLaunchRequest): ResponseDto<JobExecutionStatus>
}
