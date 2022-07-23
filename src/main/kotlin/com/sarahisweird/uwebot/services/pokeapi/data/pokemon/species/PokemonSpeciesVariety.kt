package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesVariety(
    @Json(name = "is_default") val isDefault: Boolean,
    val pokemon: NamedAPIResource<Pokemon>,
)
