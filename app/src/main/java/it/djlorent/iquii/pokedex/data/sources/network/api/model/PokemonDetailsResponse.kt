package it.djlorent.iquii.pokedex.data.sources.network.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val stats: List<PokemonBaseStat>,
    val types: List<PokemonBaseType>
)

