package it.djlorent.iquii.pokedex.data.repositories

import it.djlorent.iquii.pokedex.data.sources.local.LocalDataSource
import it.djlorent.iquii.pokedex.data.sources.network.NetworkDataSource
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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
            try {
                pokedex = networkSrc.fetchPokemons(pageSize, (page-1) * pageSize)
                localSrc.addToPokedex(pokedex)
            }catch (t: Throwable){
                println(t.message)
            }
        }

        return@withContext pokedex
    }

    override suspend fun getFavorites(page: Int, pageSize: Int): List<Pokemon> = withContext(ioDispatcher) {
        return@withContext localSrc.getFavorites(page, pageSize)
    }

    override fun getAllFavoriteIds(): Flow<List<Int>> = localSrc.getAllFavoriteIds().flowOn(ioDispatcher)

    override fun getAllFavorites(): Flow<List<Pokemon>> = localSrc.getAllFavorites().flowOn(ioDispatcher)

    override suspend fun getPokemonInfo(id: Int): Pokemon = withContext(ioDispatcher) {
        var pokemon = localSrc.getPokemonDetails(id)

        if(pokemon.stats == null || pokemon.types == null){
            try {
                pokemon = networkSrc.fetchPokemon(id.toString()) ?: pokemon
                pokemon.let{ localSrc.insertPokemonDetails(it) }
            }
            catch (t: Throwable){
                println(t.message)
            }
        }

        return@withContext pokemon
    }

    override suspend fun addFavoritePokemon(id: Int): Boolean = withContext(ioDispatcher) {
        return@withContext localSrc.addFavorite(id)
    }

    override suspend fun removeFavoritePokemon(id: Int): Boolean = withContext(ioDispatcher) {
        return@withContext localSrc.removeFavorite(id)
    }
}