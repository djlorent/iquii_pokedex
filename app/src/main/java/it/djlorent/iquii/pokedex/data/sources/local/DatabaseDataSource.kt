package it.djlorent.iquii.pokedex.data.sources.local

import it.djlorent.iquii.pokedex.data.sources.local.database.PokeDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.mappers.PokemonMapper
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseDataSource @Inject constructor(
    private val pokeDatabase: PokeDatabase
) : LocalDataSource {

    override suspend fun getPokedex(page: Int?, pageSize: Int?): List<Pokemon> {
        val localPokedex = if (page == null || pageSize == null)
            pokeDatabase.pokedexDao().getAll()
        else
            pokeDatabase.pokedexDao().getAll(page, pageSize)

        return localPokedex.map { PokemonMapper.fromLocal(it) }
    }

    override suspend fun addToPokedex(pokemons: List<Pokemon>): Boolean {
        val localPokemons = pokemons.map { PokemonMapper.toLocal(it) }
        val added = pokeDatabase.pokedexDao().insert(localPokemons)

        return added.count() == pokemons.size
    }

    override fun getAllFavoriteIds(): Flow<List<Int>> = pokeDatabase.favoritesDao().getAllIds()

    override fun getAllFavorites(): Flow<List<Pokemon>> =
        pokeDatabase.favoritesDao().getAll().map {
            it.map {
                PokemonMapper.fromLocal(it)
            }
        }

    override suspend fun getFavorites(page: Int, pageSize: Int): List<Pokemon> {
        val localFavorites = pokeDatabase.favoritesDao().getAll(page, pageSize)
        return localFavorites.map { PokemonMapper.fromLocal(it) }
    }

    override suspend fun getPokemonDetails(id: Int): Pokemon {
        val localPokemonDetails = pokeDatabase.pokedexDao().getPokemonWithDetails(id)
        return localPokemonDetails.let { PokemonMapper.fromLocalDetails(it) }
    }

    override suspend fun insertPokemonDetails(pokemon: Pokemon): Boolean {
        val localPokemonDetails = PokemonMapper.toLocalDetails(pokemon)

        val updatedTypes = pokeDatabase.pokemonTypesDao().insert(localPokemonDetails.types)
        val updatedStats = pokeDatabase.pokemonStatsDao().insert(localPokemonDetails.stats!!)
        return updatedTypes.isNotEmpty() && updatedStats > 0
    }

    override suspend fun addFavorite(pokemonId: Int): Boolean {
        val insertId = pokeDatabase.favoritesDao().insert(Favorite(pokemonId = pokemonId))
        return insertId >= 0
    }

    override suspend fun removeFavorite(pokemonId: Int): Boolean {
        val deletedCount = pokeDatabase.favoritesDao().deleteById(pokemonId)
        return deletedCount == 1
    }
}