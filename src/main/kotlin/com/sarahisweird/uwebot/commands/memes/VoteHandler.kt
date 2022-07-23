package com.sarahisweird.uwebot.commands.memes

import com.sarahisweird.uwebot.database.objects.ServerConfiguration
import com.sarahisweird.uwebot.database.objects.Vote
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.getChannelOfOrNull
import dev.kord.core.entity.*
import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.modify.embed
import org.jetbrains.exposed.sql.transactions.transaction

private val updoot = Snowflake(826104904057356298L)
private val downdoot = Snowflake(826104891843805205L)

class VoteHandler private constructor(
    private val event: ReactionChangeEvent,
) {
    private val embedThreshold = 1L

    private lateinit var customEmoji: ReactionEmoji.Custom
    private lateinit var message: Message
    private lateinit var attachment: Attachment
    private lateinit var guild: Guild
    private lateinit var author: Member

    private lateinit var dbEntry: Vote

    companion object {
        suspend fun onReactionAddEvent(addEvent: ReactionAddEvent) {
            val event = ReactionChangeEvent.fromAddEvent(addEvent)
            val voteHandler = VoteHandler(event)

            voteHandler.onReactionChangeEvent()
        }

        suspend fun onReactionRemoveEvent(removeEvent: ReactionRemoveEvent) {
            val event = ReactionChangeEvent.fromRemoveEvent(removeEvent)
            val voteHandler = VoteHandler(event)

            voteHandler.onReactionChangeEvent()
        }
    }

    private fun updateDatabaseVotes() {
        val vote = Vote.findByIdOrCreate(event.messageId)

        vote.upvotes = getActualUpvotes()
        vote.downvotes = getActualDownvotes()

        dbEntry = vote
    }

    private fun getActualUpvotes() =
        message.reactions.find { it.id == updoot }?.count?.toLong() ?: 0

    private fun getActualDownvotes() =
        message.reactions.find { it.id == downdoot }?.count?.toLong() ?: 0

    private fun getVoteScore() =
        dbEntry.upvotes - dbEntry.downvotes

    private fun findMemeChannelId() =
        ServerConfiguration.findById(guild.id.value.toLong())?.memeChannelId

    private fun setEmbedData(embedBuilder: EmbedBuilder) =
        with(embedBuilder) {
            title = "tolles meme"

            author {
                name = this@VoteHandler.author.displayName
                icon = getAvatarOfMember(this@VoteHandler.author)
            }

            image = attachment.url

            field("Upvotes", inline = true) {
                dbEntry.upvotes.toString()
            }

            field("Downvotes", inline = true) {
                dbEntry.downvotes.toString()
            }
        }

    private suspend fun onReactionChangeEvent() {
        val voteConditionChecker = VoteConditionChecker(event, listOf(updoot, downdoot))

        if (!voteConditionChecker.areConditionsMet()) return

        customEmoji = event.emoji as ReactionEmoji.Custom
        message = event.getMessage()
        attachment = message.attachments.first()

        transaction { updateDatabaseVotes() }

        val voteScore = getVoteScore()

        guild = event.getGuild()!!

        val memeChannelId = transaction { findMemeChannelId() } ?: return
        val memeChannel = guild.getChannelOfOrNull<GuildMessageChannel>(memeChannelId) ?: return
        author = message.getAuthorAsMember()!!

        if (voteScore < embedThreshold) {
            if (dbEntry.memeChannelMessageId == null) return

            val memeChannelMessage = memeChannel.getMessage(dbEntry.memeChannelMessageId!!)
            memeChannelMessage.delete()

            transaction {
                dbEntry.memeChannelMessageId = null
            }

            return
        }

        if (dbEntry.memeChannelMessageId != null) {
            val memeChannelMessage = memeChannel.getMessage(dbEntry.memeChannelMessageId!!)

            memeChannelMessage.edit {
                embed {
                    setEmbedData(this)
                }
            }

            return
        }

        val memeChannelMessage = memeChannel.createEmbed {
            setEmbedData(this)
        }

        transaction {
            dbEntry.memeChannelMessageId = memeChannelMessage.id
        }

        return
    }
}