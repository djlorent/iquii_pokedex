package it.djlorent.iquii.pokedex.data.sources.network.model

data class PokemonResponse(
    val name: String,
    val id: Int? = null,
    val url: String? = null
)