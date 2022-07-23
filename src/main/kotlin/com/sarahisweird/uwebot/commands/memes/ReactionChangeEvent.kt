package com.sarahisweird.uwebot.commands.memes

import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.GuildBehavior
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.User
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent

/**
 * Wrapper class generalizing reaction add/remove events.
 * @see ReactionAddEvent
 * @see ReactionRemoveEvent
 */
class ReactionChangeEvent private constructor(
    val guild: GuildBehavior?,
    val emoji: ReactionEmoji,
    val getUserOrNull: suspend () -> User?,
    val getMessage: suspend () -> Message,
    val getGuild: suspend () -> Guild?,
    val messageId: Snowflake,
) {
    companion object {
        fun fromAddEvent(event: ReactionAddEvent) =
            ReactionChangeEvent(
                event.guild,
                event.emoji,
                event::getUserOrNull,
                event::getMessage,
                event::getGuild,
                event.messageId
            )


        fun fromRemoveEvent(event: ReactionRemoveEvent) =
            ReactionChangeEvent(
                event.guild,
                event.emoji,
                event::getUserOrNull,
                event::getMessage,
                event::getGuild,
                event.messageId
            )
    }
}