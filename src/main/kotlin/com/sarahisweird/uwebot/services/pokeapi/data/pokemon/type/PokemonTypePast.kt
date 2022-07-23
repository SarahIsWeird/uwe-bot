package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.type

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonTypePast(
    val generation: NamedAPIResource<*>,
    val types: List<PokemonType>,
)
