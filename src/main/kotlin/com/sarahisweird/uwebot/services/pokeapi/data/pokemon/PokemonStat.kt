package com.sarahisweird.uwebot.services.pokeapi.data.pokemon

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonStat(
    val stat: NamedAPIResource<*>,
    val effort: Int,
    @Json(name = "base_stat") val baseStat: Int,
)
