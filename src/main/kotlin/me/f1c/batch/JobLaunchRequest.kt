package me.f1c.batch

data class JobLaunchRequest(
    val jobName: String,
    val parameters: Map<String, String> = emptyMap(),
)
