package com.sarahisweird.uwebot.services.pokeapi.data.pokemon

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.sarahisweird.uwebot.services.pokeapi.data.metadata.VersionGameIndex
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.helditem.PokemonHeldItem
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.move.PokemonMove
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species.PokemonSpecies
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.type.PokemonType
import com.sarahisweird.uwebot.services.pokeapi.data.pokemon.type.PokemonTypePast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pokemon(
    val id: Int,
    val name: String,
    @Json(name = "base_experience") val baseExperience: Int,
    val height: Int,
    @Json(name = "is_default") val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val forms: List<NamedAPIResource<*>>,
    @Json(name = "game_indices") val gameIndices: List<VersionGameIndex>,
    @Json(name = "held_items") val heldItems: List<PokemonHeldItem>,
    @Json(name = "location_area_encounters") val locationAreaEncounters: String,
    val moves: List<PokemonMove>,
    @Json(name = "past_types") val pastTypes: List<PokemonTypePast>,
    val sprites: PokemonSprites,
    val species: NamedAPIResource<PokemonSpecies>,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>,
)
