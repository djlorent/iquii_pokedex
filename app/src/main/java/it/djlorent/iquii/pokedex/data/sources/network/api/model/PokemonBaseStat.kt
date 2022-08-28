package it.djlorent.iquii.pokedex.data.sources.network.api.model

import com.squareup.moshi.Json

data class PokemonBaseStat(
    @Json(name = "base_stat") val baseStat: Int,
    val effort: Int,
    val stat: ModelBase
)