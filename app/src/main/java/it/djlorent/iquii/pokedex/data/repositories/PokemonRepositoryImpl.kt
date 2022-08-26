package it.djlorent.iquii.pokedex.data.repositories

import it.djlorent.iquii.pokedex.data.sources.local.LocalDataSource
import it.djlorent.iquii.pokedex.data.sources.network.NetworkDataSource
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val networkSrc: NetworkDataSource,
    private val localSrc: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : PokemonRepository {

    override suspend fun getPokedex(page: Int, pageSize: Int): List<Pokemon> = withContext(ioDispatcher) {
        var pokedex = localSrc.getPokedex(page, pageSize)

        if(pokedex.isEmpty()){
            pokedex = networkSrc.fetchPokemons(pageSize, (page-1) * pageSize)
            localSrc.addToPokedex(pokedex)
        }

        return@withContext pokedex
    }

    override suspend fun getFavorites(page: Int, pageSize: Int): List<Pokemon> = withContext(ioDispatcher) {
        return@withContext localSrc.getFavorites(page, pageSize)
    }

    override suspend fun getPokemonInfo(id: Int): Pokemon? = withContext(ioDispatcher) {
        var pokemonInfo = localSrc.getPokemonDetails(id)

        if(pokemonInfo == null){
            pokemonInfo = networkSrc.fetchPokemon(id.toString())

            pokemonInfo?.let{ localSrc.updatePokemonDetails(it) }
        }

        return@withContext pokemonInfo
    }

    override suspend fun addFavoritePokemon(id: Int): Boolean = withContext(ioDispatcher) {
        return@withContext localSrc.addFavorite(id)
    }

    override suspend fun removeFavoritePokemon(id: Int): Boolean = withContext(ioDispatcher) {
        return@withContext localSrc.removeFavorite(id)
    }
}