package it.djlorent.iquii.pokedex.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import it.djlorent.iquii.pokedex.data.sources.local.dao.FavoritesDao
import it.djlorent.iquii.pokedex.data.sources.local.dao.PokedexDao
import it.djlorent.iquii.pokedex.data.sources.local.entities.Favorite
import it.djlorent.iquii.pokedex.data.sources.local.entities.Pokemon

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