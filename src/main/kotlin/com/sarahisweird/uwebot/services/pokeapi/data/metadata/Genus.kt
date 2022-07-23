package com.sarahisweird.uwebot.services.pokeapi.data.metadata

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genus(
    val genus: String,
    val language: NamedAPIResource<*>,
)
