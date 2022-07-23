package com.sarahisweird.uwebot.services.pokeapi.data.metadata.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class APIResource(
    val url: String,
)
