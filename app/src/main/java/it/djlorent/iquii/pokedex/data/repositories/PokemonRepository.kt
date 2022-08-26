package it.djlorent.iquii.pokedex.data.repositories

import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.flow.Flow


interface PokemonRepository {

    suspend fun getPokedex(page: Int, pageSize: Int): List<Pokemon>

    suspend fun getFavorites(page: Int, pageSize: Int): List<Pokemon>

    fun getAllFavoriteIds(): Flow<List<Int>>

    fun getAllFavorites(): Flow<List<Pokemon>>

    suspend fun getPokemonInfo(id: Int): Pokemon?

    suspend fun addFavoritePokemon(id: Int): Boolean

    suspend fun removeFavoritePokemon(id: Int): Boolean
}