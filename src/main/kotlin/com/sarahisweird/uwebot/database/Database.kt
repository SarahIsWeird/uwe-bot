package com.sarahisweird.uwebot.database

import com.mysql.cj.jdbc.Driver
import com.sarahisweird.uwebot.database.tables.BankAccountTable
import com.sarahisweird.uwebot.database.tables.ServerConfigurationTable
import com.sarahisweird.uwebot.database.tables.VoteTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object UweDatabase {
    private val instance by lazy {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3306/uwebot"
            driverClassName = Driver::class.qualifiedName
            username = "uwe"
            password = "uwebot"
            maximumPoolSize = 10
        }

        val dataSource = HikariDataSource(config)

        Database.connect(dataSource)
    }

    fun init() {
        instance

        initTables()
    }

    private fun initTables() {
        transaction(instance) {
            SchemaUtils.create(
                VoteTable,
                BankAccountTable,
                ServerConfigurationTable,
            )
        }
    }

    fun dropAllTables() {
        transaction(instance) {
            SchemaUtils.drop(
                VoteTable,
                BankAccountTable,
                ServerConfigurationTable,
            )
        }
    }
}