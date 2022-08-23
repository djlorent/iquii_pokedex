package it.djlorent.iquii.pokedex.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon

@Dao
interface FavoritesDao: BaseDao<Favorite> {

    @Query("SELECT pokedex.* FROM pokedex " +
            "INNER JOIN favorites ON favorites.pokemonId = pokedex.id " +
            "ORDER BY pokedex.id ASC")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT pokedex.* FROM pokedex " +
            "INNER JOIN favorites ON favorites.pokemonId = pokedex.id " +
            "ORDER BY pokedex.id ASC " +
            "LIMIT :pageSize OFFSET :page * :pageSize")
    suspend fun getAll(page: Int, pageSize: Int): List<Pokemon>

    @Query("SELECT pokedex.* FROM pokedex " +
            "INNER JOIN favorites ON favorites.pokemonId = pokedex.id " +
            "WHERE favorites.pokemonId == :id")
    suspend fun getById(id: Int): Pokemon?

    @Query("DELETE FROM favorites")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM favorites where pokemonId == :id")
    suspend fun deleteById(id: Int): Int
}

