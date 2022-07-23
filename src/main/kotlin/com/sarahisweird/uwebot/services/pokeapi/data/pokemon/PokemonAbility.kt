package com.sarahisweird.uwebot.services.pokeapi.data.pokemon

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonAbility(
    @Json(name = "is_hidden") val isHidden: Boolean,
    val slot: Int,
    val ability: NamedAPIResource<*>,
)
