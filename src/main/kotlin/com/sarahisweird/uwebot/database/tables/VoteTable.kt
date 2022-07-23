package com.sarahisweird.uwebot.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object VoteTable : LongIdTable("votes", "message_id") {
    val upvotes = long("upvotes").default(0)
    val downvotes = long("downvotes").default(0)
    val memeChannelMessageId = long("memeChannelMessageId").nullable().default(null)
}