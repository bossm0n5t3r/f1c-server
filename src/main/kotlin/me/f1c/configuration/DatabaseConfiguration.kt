package me.f1c.configuration

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.jdbc.support.JdbcTransactionManager
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration {
    companion object {
        const val BATCH_DATA_SOURCE = "batchDataSource"
        const val BATCH_TRANSACTION_MANAGER = "batchTransactionManager"
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource() = HikariDataSource()

    @Bean
    fun database(dataSource: HikariDataSource) = Database.connect(dataSource)

    @Bean
    fun transactionManager(dataSource: DataSource) = JdbcTransactionManager(dataSource)

    @Bean(name = [BATCH_DATA_SOURCE])
    fun batchDataSource(): DataSource =
        EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("/org/springframework/batch/core/schema-drop-h2.sql")
            .addScript("/org/springframework/batch/core/schema-h2.sql")
            .build()

    @Bean(name = [BATCH_TRANSACTION_MANAGER])
    fun batchTransactionManager(batchDataSource: DataSource): JdbcTransactionManager = JdbcTransactionManager(batchDataSource)
}
