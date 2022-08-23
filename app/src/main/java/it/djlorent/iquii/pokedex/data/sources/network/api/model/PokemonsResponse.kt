package it.djlorent.iquii.pokedex.data.sources.network.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonsResponse(
    @Json(name = "count") val totalCount: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonResponse>
)
