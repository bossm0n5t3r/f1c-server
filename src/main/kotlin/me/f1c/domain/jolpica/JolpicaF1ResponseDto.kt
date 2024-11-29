package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class JolpicaF1ResponseDto<T>(
    @JsonProperty("MRData")
    val mrData: T,
)
