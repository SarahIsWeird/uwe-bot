package com.sarahisweird.uwebot.commands.memes

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Attachment
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji

class VoteConditionChecker(private val event: ReactionChangeEvent, private val voteEmojiIds: List<Snowflake>) {
    suspend fun areConditionsMet(): Boolean {
        if (isDirectMessage()) return false
        if (reactorIsBotOrSystem()) return false

        val customEmoji = event.emoji as? ReactionEmoji.Custom ?: return false

        if (!reactionEmojiIsUpvoteOrDownvote(customEmoji)) return false

        val message = event.getMessage()

        if (!messageHasOneAttachment(message)) return false

        val attachment = message.attachments.first()

        if (!attachmentIsImageOrVideo(attachment)) return false

        return true
    }

    private fun isDirectMessage() =
        event.guild == null

    private suspend fun reactorIsBotOrSystem() =
        event.getUserOrNull()?.isBot != false

    private fun reactionEmojiIsUpvoteOrDownvote(customEmoji: ReactionEmoji.Custom) =
        voteEmojiIds.contains(customEmoji.id)

    private fun messageHasOneAttachment(message: Message) =
        message.attachments.size == 1

    private fun attachmentIsImageOrVideo(attachment: Attachment) =
        (attachment.width ?: 0) >= 1
}