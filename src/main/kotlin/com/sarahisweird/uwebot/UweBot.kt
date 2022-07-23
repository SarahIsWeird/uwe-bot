package com.sarahisweird.uwebot

import com.sarahisweird.uwebot.database.UweDatabase
import com.sarahisweird.uwebot.services.pokeapi.PokeApiService
import dev.kord.common.annotation.KordPreview
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import me.jakejmattson.discordkt.dsl.bot

@OptIn(KordPreview::class, PrivilegedIntent::class)
fun main(args: Array<String>) {
    if (args.getOrNull(0)?.equals("--delete-database") == true) {
        UweDatabase.init()
        UweDatabase.dropAllTables()

        return
    }

    val token = System.getenv("UWEBOT_TOKEN")

    UweDatabase.init()

    PokeApiService.impl

    bot(token) {
        configure {
            intents = Intents.all
        }

        prefix { "uwe " }
    }
}