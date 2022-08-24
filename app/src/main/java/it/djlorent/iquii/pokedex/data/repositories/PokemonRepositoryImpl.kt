package it.djlorent.iquii.pokedex.data.repositories

import androidx.annotation.WorkerThread
import it.djlorent.iquii.pokedex.data.sources.local.LocalDataSource
import it.djlorent.iquii.pokedex.data.sources.network.NetworkDataSource
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val networkSrc: NetworkDataSource,
    private val localSrc: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : PokemonRepository {

    override fun getPokedex(page: Int, pageSize: Int): Flow<List<Pokemon>> = flow {
        var pokedex = localSrc.getPokedex(page, pageSize)

        if(pokedex.isEmpty()){
            pokedex = networkSrc.fetchPokemons(pageSize, (page-1) * pageSize)
            localSrc.addToPokedex(pokedex)
        }
        emit(pokedex)
    }.flowOn(ioDispatcher)

    override fun getFavorites(page: Int, pageSize: Int): Flow<List<Pokemon>> = flow {
        val favorites = localSrc.getFavorites(page, pageSize)
        emit(favorites)
    }.flowOn(ioDispatcher)

    override suspend fun getPokemonInfo(id: Int): Pokemon? {
        var pokemonInfo = localSrc.getPokemonDetails(id)

        if(pokemonInfo == null){
            pokemonInfo = networkSrc.fetchPokemon(id.toString())

            pokemonInfo?.let{ localSrc.updatePokemonDetails(it) }
        }

        return pokemonInfo
    }

    @WorkerThread
    override suspend fun addFavoritePokemon(id: Int): Boolean = localSrc.addFavorite(id)

    @WorkerThread
    override suspend fun removeFavoritePokemon(id: Int): Boolean = localSrc.removeFavorite(id)
}