package com.sarahisweird.uwebot.services.pokeapi.impl

import com.sarahisweird.uwebot.services.pokeapi.PokeApiService
import com.sarahisweird.uwebot.services.pokeapi.RandomPokemonSprite
import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResourceList
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.Pokemon
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.PokemonSprites
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species.PokemonSpecies
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.toImmutableList
import java.io.IOException
import kotlin.random.Random
import kotlin.random.nextInt

class PokeApiServiceImpl(
    private val client: OkHttpClient,
    private val moshi: Moshi
) : PokeApiService {
    private val apiUrl = "https://pokeapi.co/api/v2"

    private val random = Random(System.currentTimeMillis())

    private val speciesResourceList by lazy {
        val list = mutableListOf<NamedAPIResource<PokemonSpecies>>()
        var next: String? = "$apiUrl/pokemon-species"
        var i = 0

        while (next != null) {
            val request = Request.Builder()
                .url(next!!)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Error fetching $next")

                val resources = moshi.adapter<NamedAPIResourceList<PokemonSpecies>>(Types.newParameterizedType(NamedAPIResourceList::class.java, PokemonSpecies::class.java))
                    .fromJson(response.body!!.source()) ?: throw RuntimeException("Couldn't decode resource list")

                list.addAll(resources.results)
                next = resources.next
                i += resources.results.size

                println("${i.toDouble() / resources.count.toDouble() * 100.0}%")

                runBlocking { delay(250) }
            }
        }

        list.toImmutableList()
    }

    private val pokemonSpeciesList = speciesResourceList.map { null }.toMutableList<PokemonSpecies?>()
    private val pokemonList = speciesResourceList.map { null }.toMutableList<Pokemon?>()

    override fun getPokemon(id: Int): Pokemon {
        if (pokemonList[id] == null) {
            val species = getPokemonSpecies(id)

            val defaultVariety = species.varieties.find { it.isDefault } ?: species.varieties.first()
            val pokemonResource = defaultVariety.pokemon

            val request = Request.Builder()
                .url(pokemonResource.url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Error fetching ${pokemonResource.url}")

                pokemonList[id] = moshi.adapter(Pokemon::class.java).fromJson(response.body!!.source())
                    ?: throw RuntimeException("Couldn't decode pokemon")
            }
        }

        return pokemonList[id]!!
    }

    override fun getRandomPokemon(): Pokemon =
        getPokemon(random.nextInt(0 until pokemonList.size))

    override fun getPokemonSpecies(id: Int): PokemonSpecies {
        if (pokemonSpeciesList[id] == null) {
            val resource = speciesResourceList[id]

            val request = Request.Builder()
                .url(resource.url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Error fetching ${resource.url}")

                pokemonSpeciesList[id] = moshi.adapter(PokemonSpecies::class.java)
                    .fromJson(response.body!!.source()) ?: throw RuntimeException("Couldn't decode pokemon species")
            }
        }

        return pokemonSpeciesList[id]!!
    }

    override fun getPokemonSprites(id: Int): PokemonSprites {
        val pokemon = getPokemon(id)

        return pokemon.sprites
    }

    override fun getPokemonSprite(id: Int): RandomPokemonSprite {
        val species = getPokemonSpecies(id)

        val isShiny = random.nextInt(0 until 4096) == 0
        val isFemale = species.hasGenderDifferences and (random.nextDouble() < (species.genderRate * 0.125))

        val sprites = getPokemonSprites(id)

        val spriteUrl = if (!isShiny and !isFemale) {
            sprites.frontDefault
        } else if (!isShiny and isFemale) {
            sprites.frontFemale!!
        } else if (isShiny and !isFemale) {
            sprites.frontShiny
        } else {
            sprites.frontShinyFemale!!
        }

        return RandomPokemonSprite(spriteUrl, isShiny, isFemale)
    }
}