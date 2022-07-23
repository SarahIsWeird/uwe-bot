package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.helditem

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonHeldItem(
    val item: NamedAPIResource<*>,
    @Json(name = "version_details") val versionDetails: List<PokemonHeldItemVersion>,
)
