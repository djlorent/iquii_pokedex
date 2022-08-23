package it.djlorent.iquii.pokedex.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokedex")
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
)