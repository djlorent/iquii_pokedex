package it.djlorent.iquii.pokedex.data.sources.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "pokemonTypes",
    primaryKeys = ["pokemonId", "type"],
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("pokemonId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonType(
    val pokemonId: Int,
    val type: String,
)