package it.djlorent.iquii.pokedex.data.repositories

import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.flow.Flow


interface PokemonRepository {

    fun getPokedex(page: Int, pageSize: Int): Flow<List<Pokemon>>

    fun getFavorites(page: Int, pageSize: Int): Flow<List<Pokemon>>

    suspend fun getPokemonInfo(id: Int): Pokemon?

    suspend fun addFavoritePokemon(id: Int): Boolean

    suspend fun removeFavoritePokemon(id: Int): Boolean
}