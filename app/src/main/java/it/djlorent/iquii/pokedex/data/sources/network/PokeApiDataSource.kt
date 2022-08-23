package it.djlorent.iquii.pokedex.data.sources.network

import it.djlorent.iquii.pokedex.data.sources.network.api.PokemonService
import it.djlorent.iquii.pokedex.mappers.PokemonMapper
import it.djlorent.iquii.pokedex.models.Pokemon
import javax.inject.Inject

class PokeApiDataSource @Inject constructor(
    private val pokemonService: PokemonService
) : NetworkDataSource {

    override suspend fun fetchPokemons(limit: Int, offset: Int): List<Pokemon> {
        val response = pokemonService.fetchPokemons(limit, offset)
        val results = response.body()?.results

        return results?.map { PokemonMapper.fromNetwork(it) } ?: listOf()
    }

    override suspend fun fetchPokemon(idOrName: String): Pokemon? {
        val response = pokemonService.fetchPokemon(idOrName)
        val result = response.body()

        return if (result == null) null else PokemonMapper.fromNetwork(result)
    }
}