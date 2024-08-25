package me.f1c.domain

import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.toEntity
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.time.Clock
import java.time.LocalDateTime

fun currentKotlinLocalDateTimeUtc(): kotlinx.datetime.LocalDateTime = LocalDateTime.now(Clock.systemUTC()).toKotlinLocalDateTime()

abstract class BaseLongIdTable(
    name: String,
) : LongIdTable(name) {
    val createdAt = datetime("created_at").clientDefault { currentKotlinLocalDateTimeUtc() }
    val updatedAt = datetime("updated_at").nullable()
}

abstract class BaseLongEntity(
    id: EntityID<Long>,
    table: BaseLongIdTable,
) : LongEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
}

abstract class BaseLongEntityClass<E : BaseLongEntity>(
    table: BaseLongIdTable,
) : LongEntityClass<E>(table) {
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = currentKotlinLocalDateTimeUtc()
                } catch (e: Exception) {
                    // nothing much to do here
                }
            }
        }
    }
}
