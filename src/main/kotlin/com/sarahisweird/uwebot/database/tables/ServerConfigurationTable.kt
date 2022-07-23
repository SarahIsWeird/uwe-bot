package com.sarahisweird.uwebot.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object ServerConfigurationTable : LongIdTable("server_configuration", "server_id") {
    val memeChannelId = long("meme_channel_id").nullable().default(null)
}