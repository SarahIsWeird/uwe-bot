package com.sarahisweird.uwebot.database.objects

import com.sarahisweird.uwebot.database.tables.ServerConfigurationTable
import dev.kord.common.entity.Snowflake
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ServerConfiguration(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ServerConfiguration>(ServerConfigurationTable) {
        fun findByIdOrCreate(serverId: Snowflake) =
            serverId.value.toLong().let { findById(it) ?: new(it) {} }
    }

    var memeChannelId by ServerConfigurationTable.memeChannelId
        .transform({ it?.value?.toLong() }, { it?.let { Snowflake(it.toULong()) } })
}