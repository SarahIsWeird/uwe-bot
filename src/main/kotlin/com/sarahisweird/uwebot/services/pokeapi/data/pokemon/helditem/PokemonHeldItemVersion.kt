package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.helditem

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonHeldItemVersion(
    val version: NamedAPIResource<*>,
    val rarity: Int,
)
