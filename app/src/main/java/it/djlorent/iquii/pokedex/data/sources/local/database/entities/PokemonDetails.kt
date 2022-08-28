package it.djlorent.iquii.pokedex.data.sources.local.database.entities

import androidx.room.*

data class PokemonDetails(
    @PrimaryKey
    @Embedded val pokemon: Pokemon,

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val types: List<PokemonType>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val stats: PokemonStats?
)

