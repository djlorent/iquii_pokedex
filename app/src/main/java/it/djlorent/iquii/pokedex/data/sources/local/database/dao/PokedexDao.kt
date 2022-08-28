package it.djlorent.iquii.pokedex.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonDetails

@Dao
interface PokedexDao: BaseDao<Pokemon> {

    @Query("SELECT * FROM pokedex ORDER BY id ASC")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokedex ORDER BY id ASC LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    suspend fun getAll(page: Int, pageSize: Int): List<Pokemon>

    @Query("SELECT * FROM pokedex WHERE id == :id")
    suspend fun getById(id: Int): Pokemon?

    @Query("SELECT * FROM pokedex WHERE name == :name")
    suspend fun getByName(name: String): Pokemon?

    @Query("DELETE FROM pokedex")
    suspend fun deleteAll()

    @Query("DELETE FROM pokedex where id == :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM pokedex where name == :name")
    suspend fun deleteByName(name: String)

    @Transaction
    @Query("SELECT * FROM pokedex")
    suspend fun getAllPokemonWithDetails(): List<PokemonDetails>

    @Transaction
    @Query("SELECT * FROM pokedex WHERE id = :pokemonId")
    suspend fun getPokemonWithDetails(pokemonId: Int): PokemonDetails
}