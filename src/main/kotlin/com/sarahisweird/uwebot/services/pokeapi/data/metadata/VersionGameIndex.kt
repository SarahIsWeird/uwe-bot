package com.sarahisweird.uwebot.services.pokeapi.data.metadata

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VersionGameIndex(
    @Json(name = "game_index") val gameIndex: Int,
    val version: NamedAPIResource<*>,
)
