package com.sarahisweird.uwebot.services.pokeapi.data.metadata.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NamedAPIResourceList<T : Any>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedAPIResource<T>>,
)
