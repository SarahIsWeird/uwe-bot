package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesDexEntry(
    @Json(name = "entry_number") val entryNumber: Int,
    val pokedex: NamedAPIResource<*>,
)
