package it.djlorent.iquii.pokedex.data.sources.local

import it.djlorent.iquii.pokedex.models.Pokemon

interface LocalDataSource {

    suspend fun getPokedex(page: Int? = null, pageSize: Int? = null): List<Pokemon>
    suspend fun addToPokedex(pokemons: List<Pokemon>): Boolean

    suspend fun getFavorites(page: Int? = null, pageSize: Int? = null): List<Pokemon>
    suspend fun addFavorite(pokemonId: Int): Boolean
    suspend fun removeFavorite(pokemonId: Int): Boolean

    suspend fun getPokemonDetails(id: Int): Pokemon?
    suspend fun updatePokemonDetails(pokemon: Pokemon): Boolean
}

