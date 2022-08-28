package it.djlorent.iquii.pokedex.data.sources.local

import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getPokedex(page: Int? = null, pageSize: Int? = null): List<Pokemon>
    suspend fun addToPokedex(pokemons: List<Pokemon>): Boolean

    fun getAllFavoriteIds(): Flow<List<Int>>
    fun getAllFavorites(): Flow<List<Pokemon>>
    suspend fun getFavorites(page: Int, pageSize: Int): List<Pokemon>
    suspend fun addFavorite(pokemonId: Int): Boolean
    suspend fun removeFavorite(pokemonId: Int): Boolean

    suspend fun getPokemonDetails(id: Int): Pokemon
    suspend fun insertPokemonDetails(pokemon: Pokemon): Boolean
}

