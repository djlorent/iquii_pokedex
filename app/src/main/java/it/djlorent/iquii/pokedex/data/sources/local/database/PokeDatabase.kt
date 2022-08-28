package it.djlorent.iquii.pokedex.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.*
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.*

@Database(
    entities = [
        Pokemon::class,
        Favorite::class,
        PokemonStats::class,
        PokemonType::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokeDatabase : RoomDatabase() {

    abstract fun pokedexDao(): PokedexDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun pokemonStatsDao(): PokemonStatsDao
    abstract fun pokemonTypesDao(): PokemonTypesDao

    companion object {
        const val DATABASE_NAME = "Poke.db"
    }
}