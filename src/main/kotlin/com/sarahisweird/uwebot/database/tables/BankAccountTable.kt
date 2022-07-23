package com.sarahisweird.uwebot.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object BankAccountTable : LongIdTable("accounts", "user_id") {
    val funds = long("funds").default(0)
}