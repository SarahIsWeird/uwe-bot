package com.sarahisweird.uwebot.database.objects

import com.sarahisweird.uwebot.database.tables.BankAccountTable
import dev.kord.common.entity.Snowflake
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BankAccount(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<BankAccount>(BankAccountTable) {
        fun findByIdOrCreate(id: Snowflake) =
            id.value.toLong().let { findById(it) ?: new(it) {} }
    }

    var funds by BankAccountTable.funds
}