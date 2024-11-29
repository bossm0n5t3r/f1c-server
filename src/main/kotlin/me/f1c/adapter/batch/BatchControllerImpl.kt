package me.f1c.adapter.batch

import me.f1c.domain.ResponseDto
import me.f1c.domain.batch.BatchService
import me.f1c.domain.batch.JobExecutionStatus
import me.f1c.domain.batch.JobLaunchRequest
import me.f1c.domain.toResponseDto
import me.f1c.port.batch.BatchController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/batch")
class BatchControllerImpl(
    private val batchService: BatchService,
) : BatchController {
    @PostMapping("/execute-job")
    override fun executeJob(
        @RequestBody jobLaunchRequest: JobLaunchRequest,
    ): ResponseDto<JobExecutionStatus> = batchService.executeJob(jobLaunchRequest).toResponseDto()
}
