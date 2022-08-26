package it.djlorent.iquii.pokedex.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao: BaseDao<Favorite> {

    @Query("SELECT pokemonId FROM favorites ORDER BY id ASC")
    fun getAllIds(): Flow<List<Int>>

    @Query("SELECT pokedex.* FROM pokedex " +
            "INNER JOIN favorites ON favorites.pokemonId = pokedex.id " +
            "ORDER BY pokedex.id ASC ")
    fun getAll(): Flow<List<Pokemon>>

    @Query("SELECT pokedex.* FROM pokedex " +
            "INNER JOIN favorites ON favorites.pokemonId = pokedex.id " +
            "ORDER BY pokedex.id ASC " +
            "LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
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

