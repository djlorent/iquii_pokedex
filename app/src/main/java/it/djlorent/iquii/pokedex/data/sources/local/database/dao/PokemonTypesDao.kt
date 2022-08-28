package it.djlorent.iquii.pokedex.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonType

@Dao
interface PokemonTypesDao: BaseDao<PokemonType> {

    @Query("SELECT * FROM pokemonTypes WHERE pokemonId == :id")
    suspend fun getById(id: Int): List<PokemonType>

    @Query("DELETE FROM pokemonTypes")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM pokemonTypes where pokemonId == :id")
    suspend fun deleteById(id: Int): Int
}