package com.sarahisweird.uwebot.services.pokeapi

import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.Pokemon
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.PokemonSprites
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species.PokemonSpecies
import com.sarahisweird.uwebot.services.pokeapi.impl.PokeApiServiceImpl
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.nio.file.Files

interface PokeApiService {
    companion object {
        val impl by lazy {
            val client = OkHttpClient.Builder()
                .cache(Cache(
                    directory = Files.createTempDirectory("uwe").toFile(),
                    maxSize = 10L * 1024L * 1024L, // 10 MiB
                ))
                .build()
            val moshi = Moshi.Builder().build()

            PokeApiServiceImpl(client, moshi)
        }
    }

//    fun getPokemon(): List<Pokemon>
    fun getPokemon(id: Int): Pokemon
    fun getRandomPokemon(): Pokemon

//    fun getPokemonSpecies(): List<PokemonSpecies>
    fun getPokemonSpecies(id: Int): PokemonSpecies

    fun getPokemonSprites(id: Int): PokemonSprites
    fun getPokemonSprite(id: Int): RandomPokemonSprite
}

data class RandomPokemonSprite(
    val spriteUrl: String,
    val isShiny: Boolean,
    val isFemale: Boolean,
)