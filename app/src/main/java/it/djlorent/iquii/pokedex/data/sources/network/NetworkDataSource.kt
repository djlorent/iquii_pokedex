package it.djlorent.iquii.pokedex.data.sources.network

import it.djlorent.iquii.pokedex.models.Pokemon

interface NetworkDataSource {

    suspend fun fetchPokemons(limit: Int, offset: Int): List<Pokemon>

    suspend fun fetchPokemon(idOrName: String): Pokemon?
}