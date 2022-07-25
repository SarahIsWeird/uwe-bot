package com.sarahisweird.uwebot.commands.pokemon

import com.sarahisweird.uwebot.commands.util.DamerauLevenshtein
import com.sarahisweird.uwebot.database.objects.BankAccount
import com.sarahisweird.uwebot.services.pokeapi.PokeApiService
import com.sarahisweird.uwebot.services.pokeapi.RandomPokemonSprite
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.Pokemon
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species.PokemonSpecies
import dev.kord.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.TypeContainer
import me.jakejmattson.discordkt.arguments.AnyArg
import me.jakejmattson.discordkt.commands.SlashCommandEvent
import me.jakejmattson.discordkt.conversations.conversation
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.pow
import kotlin.random.Random

class GuessingSession<T : TypeContainer>(
    private val event: SlashCommandEvent<T>,
) {
    private val pokeApiService = PokeApiService.impl

    private val pokemon: Pokemon = pokeApiService.getRandomPokemon()
    private val species: PokemonSpecies = pokeApiService.getPokemonSpecies(pokemon.id)
    private val sprite: RandomPokemonSprite = pokeApiService.getPokemonSprite(pokemon.id)

    private val germanName = species.names.find { it.language.name == "de" }!!.name
    private val englishName = species.names.find { it.language.name == "en" }!!.name

    private lateinit var guess: String

    private data class DamerauLevenshteinDistances(
        val german: Int,
        val english: Int,
    )

    suspend fun start() {
        with(event) {
            promptGuess()

            val dlDistances = calculateDamerauLevenshteinDistances()

            if (isResponseFullyCorrect(dlDistances)) {
                onGuessCorrect()
            } else if (responseIsPartiallyCorrect(dlDistances)) {
                onGuessPartiallyCorrect(dlDistances)
            } else {
                onGuessIncorrect()
            }
        }
    }

    private suspend fun SlashCommandEvent<T>.promptGuess() {
        createGuessingConversation().startSlashResponse(discord, author, this)
    }

    private fun calculateDamerauLevenshteinDistances(): DamerauLevenshteinDistances {
        val dlGerman = DamerauLevenshtein.of(germanName.lowercase(), guess.lowercase())
        val dlEnglish = DamerauLevenshtein.of(englishName.lowercase(), guess.lowercase())

        return DamerauLevenshteinDistances(dlGerman, dlEnglish)
    }

    private fun SlashCommandEvent<T>.createGuessingConversation() =
        conversation {
            guess = prompt(AnyArg) {
                setTitleAndImage()
            }
        }

    private fun isResponseFullyCorrect(dlDistances: DamerauLevenshteinDistances) =
        (dlDistances.german == 0) or (dlDistances.english == 0)

    private fun responseIsPartiallyCorrect(dlDistances: DamerauLevenshteinDistances) =
        (dlDistances.german < germanName.lowercase().length) and (dlDistances.english < englishName.lowercase().length)

    private suspend fun SlashCommandEvent<T>.onGuessCorrect() {
        val reward = generateRewardForCatch()

        respond {
            setTitleAndImage()
            setSpeciesNames()

            description = "Du hast das Pokemon richtig erraten! Du erhältst ₽$reward."
        }
    }

    private suspend fun SlashCommandEvent<T>.onGuessPartiallyCorrect(dlDistances: DamerauLevenshteinDistances) {
        val reward = generatePartialRewardForCatch(dlDistances)

        val germanPercentage = calculateGermanMatchPercentage(dlDistances.german)
        val englishPercentage = calculateEnglishMatchPercentage(dlDistances.english)

        val germanPercentageBetter = germanPercentage >= englishPercentage

        respond {
            setTitleAndImage()
            setSpeciesNames()

            description = "Deine Antwort enthält Fehler! Du erhältst trotzdem ₽$reward."

            field("% richtig", inline = true) {
                percentageToString(
                    if (germanPercentageBetter) germanPercentage else englishPercentage,
                    decimalPlaces = 2
                )
            }
        }
    }

    private fun percentageToString(percentage: Double, decimalPlaces: Int) =
        ((percentage * 10.0.pow(decimalPlaces + 2)).toLong().toDouble() / 10.0.pow(decimalPlaces)).toString()

    private suspend fun SlashCommandEvent<T>.onGuessIncorrect() {
        respond {
            setTitleAndImage()
            setSpeciesNames()

            description = "Deine Antwort war komplett falsch! :("
        }
    }

    private fun generateRewardForCatch(): Long {
        var reward = Random.nextLong(2400, 2600)

        if (sprite.isShiny) {
            reward *= 2
        }

        transaction {
            val bankAccount = BankAccount.findByIdOrCreate(event.author.id)

            bankAccount.funds = reward
        }

        return reward
    }

    private fun generatePartialRewardForCatch(dlDistances: DamerauLevenshteinDistances): Long {
        var reward = Random.nextLong(2400, 2600)

        if (sprite.isShiny) {
            reward *= 2
        }

        val germanPercentage = calculateGermanMatchPercentage(dlDistances.german)
        val englishPercentage = calculateEnglishMatchPercentage(dlDistances.english)

        reward = (reward.toDouble() * maxOf(germanPercentage, englishPercentage)).toLong()

        transaction {
            val bankAccount = BankAccount.findByIdOrCreate(event.author.id)

            bankAccount.funds = reward
        }

        return reward
    }

    private fun calculateGermanMatchPercentage(germanDistance: Int) =
        1.0 - germanDistance.toDouble() / germanName.length.toDouble()

    private fun calculateEnglishMatchPercentage(englishDistance: Int) =
        1.0 - englishDistance.toDouble() / englishName.length.toDouble()

    private fun EmbedBuilder.setTitleAndImage() {
        title = "Errate das Pokemon!"

        image = sprite.spriteUrl
    }

    private fun EmbedBuilder.setSpeciesNames() {
        field("Deutscher Name", true) { germanName }
        field("Englischer Name", true) { englishName }
    }
}