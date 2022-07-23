package com.sarahisweird.uwebot.commands.economy

import com.sarahisweird.uwebot.database.objects.BankAccount
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions
import dev.kord.common.entity.Snowflake
import me.jakejmattson.discordkt.TypeContainer
import me.jakejmattson.discordkt.arguments.UserArg
import me.jakejmattson.discordkt.commands.GuildSlashCommandEvent
import me.jakejmattson.discordkt.commands.commands
import org.jetbrains.exposed.sql.transactions.transaction

fun economyCommands() = commands("Economy") {
    slash("money", "Money") {
        requiredPermissions = Permissions(Permission.UseApplicationCommands)

        execute(UserArg.optional { it.author }) {
            respondFundsOf(args.first.id)
        }
    }
}

private suspend fun <T : TypeContainer> GuildSlashCommandEvent<T>.respondFundsOf(userId: Snowflake) {
    val account = transaction { BankAccount.findByIdOrCreate(userId) }

    respondPublic {
        title = "Bankaccount"

        field("Pokedollar", true) {
            "₽" + account.funds.toString()
        }
    }
}