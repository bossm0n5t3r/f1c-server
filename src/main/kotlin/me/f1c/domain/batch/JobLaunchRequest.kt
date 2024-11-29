package me.f1c.domain.batch

data class JobLaunchRequest(
    val jobName: String,
    val parameters: Map<String, String> = emptyMap(),
)
