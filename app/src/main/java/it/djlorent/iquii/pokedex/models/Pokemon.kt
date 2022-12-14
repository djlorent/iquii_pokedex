package it.djlorent.iquii.pokedex.models

data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val types: List<PokemonType>? = null,
    val stats: PokemonStats? = null,
)

