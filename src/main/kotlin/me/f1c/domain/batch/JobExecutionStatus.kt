package me.f1c.domain.batch

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus

data class JobExecutionStatus(
    val jobName: String,
    val jobExecutionId: Long,
    val batchStatus: BatchStatus,
    val exitStatus: ExitStatus,
    val parameters: Map<String, String>,
)
