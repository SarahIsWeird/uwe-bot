package com.sarahisweird.uwebot.database.objects

import com.sarahisweird.uwebot.database.tables.VoteTable
import dev.kord.common.entity.Snowflake
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Vote(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<Vote>(VoteTable) {
        fun findByIdOrCreate(id: Snowflake) =
            id.value.toLong().let { Vote.findById(it) ?: Vote.new(it) {} }
    }

    var upvotes by VoteTable.upvotes
    var downvotes by VoteTable.downvotes

    var memeChannelMessageId by VoteTable.memeChannelMessageId
        .transform({ it?.value?.toLong() }, { it?.let { Snowflake(it.toULong()) } })
}