package it.djlorent.iquii.pokedex.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonStats

@Dao
interface PokemonStatsDao: BaseDao<PokemonStats> {

    @Query("SELECT * FROM pokemonStats WHERE id == :id")
    suspend fun getById(id: Int): PokemonStats?

    @Query("DELETE FROM pokemonStats")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM pokemonStats where id == :id")
    suspend fun deleteById(id: Int): Int
}

