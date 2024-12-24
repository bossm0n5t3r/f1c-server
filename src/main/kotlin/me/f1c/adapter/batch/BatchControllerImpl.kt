package me.f1c.adapter.batch

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.ResponseDto
import me.f1c.domain.batch.BatchService
import me.f1c.domain.batch.JobExecutionStatus
import me.f1c.domain.batch.JobLaunchRequest
import me.f1c.domain.toResponseDto
import me.f1c.port.batch.BatchController
import org.springframework.http.HttpMethod
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
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

    private fun HttpMethod.log(params: Any? = null) {
        LOGGER.info("{} test: {}, {}, {}", LogResult.SUCCEEDED, this, params, SecurityContextHolder.getContext().authentication)
    }

    @GetMapping("/test")
    fun testGet(): ResponseDto<String> {
        HttpMethod.GET.log()
        return "test".toResponseDto()
    }

    @PostMapping("/test")
    fun testPost(
        @RequestBody dummyRequest: Any,
    ): ResponseDto<Any> {
        HttpMethod.POST.log(dummyRequest)
        return dummyRequest.toResponseDto()
    }
}
