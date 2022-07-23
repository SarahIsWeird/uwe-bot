package com.sarahisweird.uwebot.services.pokeapi.data.pokemon.species

import com.sarahisweird.uwebot.services.pokeapi.data.metadata.*
import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.APIResource
import com.sarahisweird.uwebot.services.pokeapi.data.metadata.api.NamedAPIResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpecies(
    val id: Int,
    val name: String,
    val order: Int?,
    @Json(name = "gender_rate") val genderRate: Int,
    @Json(name = "capture_rate") val captureRate: Int?,
    @Json(name = "base_happiness") val baseHappiness: Int?,
    @Json(name = "is_baby") val isBaby: Boolean?,
    @Json(name = "is_legendary") val isLegendary: Boolean?,
    @Json(name = "is_mythical") val isMythical: Boolean?,
    @Json(name = "hatch_counter") val hatchCounter: Int?,
    @Json(name = "has_gender_differences") val hasGenderDifferences: Boolean,
    @Json(name = "forms_switchable") val formsSwitchable: Boolean?,
    @Json(name = "growth_rate") val growthRate: NamedAPIResource<*>?,
    @Json(name = "pokedex_numbers") val pokedexNumbers: List<PokemonSpeciesDexEntry>?,
    @Json(name = "egg_groups") val eggGroups: List<NamedAPIResource<*>>?,
    val color: NamedAPIResource<*>?,
    val shape: NamedAPIResource<*>?,
    @Json(name = "evolves_from_species") val evolvesFromSpecies: NamedAPIResource<PokemonSpecies>?,
    @Json(name = "evolution_chain") val evolutionChain: APIResource?,
    val habitat: NamedAPIResource<*>?,
    val generation: NamedAPIResource<*>?,
    val names: List<Name>,
    @Json(name = "pal_park_encounters") val palParkEncounters: List<PalParkEncounterArea>?,
    @Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorText>?,
    @Json(name = "form_descriptions") val formDescriptions: List<Description>?,
    val genera: List<Genus>?,
    val varieties: List<PokemonSpeciesVariety>
)
