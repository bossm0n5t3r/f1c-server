package me.f1c

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class F1CServerApplication

fun main(args: Array<String>) {
    runApplication<F1CServerApplication>(*args)
}
