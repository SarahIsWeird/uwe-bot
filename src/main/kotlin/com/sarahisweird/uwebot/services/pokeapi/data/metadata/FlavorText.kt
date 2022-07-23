package com.sarahisweird.uwebot.services.pokeapi.data.metadata

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlavorText(
    @Json(name = "flavor_text") val flavorText: String,
    val language: NamedAPIResource<*>,
    val version: NamedAPIResource<*>,
)
