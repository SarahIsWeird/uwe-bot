package com.sarahisweird.uwebot.services.pokeapi.data.metadata

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Description(
    val description: String,
    val language: NamedAPIResource<*>,
)
