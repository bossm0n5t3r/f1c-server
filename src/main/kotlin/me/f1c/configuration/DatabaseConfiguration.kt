package me.f1c.configuration

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource() = HikariDataSource()

    @Bean
    fun database(dataSource: HikariDataSource) = Database.connect(dataSource)
}
