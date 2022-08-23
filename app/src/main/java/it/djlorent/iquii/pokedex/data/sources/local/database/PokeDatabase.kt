package it.djlorent.iquii.pokedex.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.FavoritesDao
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.PokedexDao
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon

@Database(
    entities = [
        Pokemon::class,
        Favorite::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokeDatabase : RoomDatabase() {

    abstract fun pokedexDao(): PokedexDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        const val DATABASE_NAME = "Poke.db"
    }
}