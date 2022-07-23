package com.sarahisweird.uwebot.commands.memes

import com.sarahisweird.uwebot.database.objects.ServerConfiguration
import dev.kord.core.behavior.channel.asChannelOf
import dev.kord.core.entity.Member
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent
import me.jakejmattson.discordkt.arguments.ChannelArg
import me.jakejmattson.discordkt.commands.commands
import me.jakejmattson.discordkt.dsl.listeners
import org.jetbrains.exposed.sql.transactions.transaction

fun memeVotingCommands() = commands("Meme Voting") {
    slash("setmemechannel") {
        execute(ChannelArg.optional { it.channel.asChannelOf() }) {
            transaction {
                val config = ServerConfiguration.findByIdOrCreate(guild.id)
                config.memeChannelId = args.first.id
            }

            respond("Successfully set the current channel as the meme channel.")
        }
    }
}

fun votingListeners() = listeners {
    on<ReactionAddEvent> {
        VoteHandler.onReactionAddEvent(this)
    }

    on<ReactionRemoveEvent> {
        VoteHandler.onReactionRemoveEvent(this)
    }
}

fun getAvatarOfMember(member: Member) =
    if (member.memberAvatar != null) {
        member.memberAvatar!!.url
    } else if (member.avatar != null) {
        member.avatar!!.url
    } else {
        member.defaultAvatar.url
    }