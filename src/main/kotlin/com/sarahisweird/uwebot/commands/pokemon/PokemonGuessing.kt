package com.sarahisweird.uwebot.commands.pokemon

import me.jakejmattson.discordkt.commands.commands

fun guessingCommands() = commands("Pokemon Guessing") {
    slash("pokemon") {
        execute {
            GuessingSession(this).start()
        }
    }
}