package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.move

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonMove(
    val move: NamedAPIResource<*>,
    @Json(name = "version_group_details") val versionGroupDetails: List<PokemonMoveVersion>,
)
