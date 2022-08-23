package it.djlorent.iquii.pokedex.data.sources.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = [
        Index(value = ["pokemonId"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("pokemonId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
)

