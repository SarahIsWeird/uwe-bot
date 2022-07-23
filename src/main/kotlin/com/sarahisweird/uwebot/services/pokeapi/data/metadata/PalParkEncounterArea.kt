package com.sarahisweird.uwebot.services.pokeapi.data.metadata

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PalParkEncounterArea(
    @Json(name = "base_score") val baseScore: Int,
    val rate: Int,
    val area: NamedAPIResource<*>,
)
