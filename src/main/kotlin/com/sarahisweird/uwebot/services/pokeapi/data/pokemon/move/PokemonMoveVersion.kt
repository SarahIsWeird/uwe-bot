package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.move

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonMoveVersion(
    @Json(name = "move_learn_method") val moveLearnMethod: NamedAPIResource<*>,
    @Json(name = "version_group") val versionGroup: NamedAPIResource<*>,
    @Json(name = "level_learned_at") val levelLearnedAt: Int,
)
