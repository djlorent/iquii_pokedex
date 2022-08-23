package it.djlorent.iquii.pokedex.data.sources.local

import it.djlorent.iquii.pokedex.data.sources.local.database.PokeDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.mappers.PokemonMapper
import it.djlorent.iquii.pokedex.models.Pokemon
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

    override suspend fun getFavorites(page: Int?, pageSize: Int?): List<Pokemon> {
        val localFavorites = if (page == null || pageSize == null)
            pokeDatabase.favoritesDao().getAll()
        else
            pokeDatabase.favoritesDao().getAll(page, pageSize)

        return localFavorites.map { PokemonMapper.fromLocal(it) }
    }

    override suspend fun getPokemonDetails(id: Int): Pokemon? {
        val localPokemon = pokeDatabase.pokedexDao().getById(id)

        return localPokemon?.let { PokemonMapper.fromLocal(it) }
    }

    override suspend fun updatePokemonDetails(pokemon: Pokemon): Boolean {
        val localPokemon = PokemonMapper.toLocal(pokemon)
        val updated = pokeDatabase.pokedexDao().update(localPokemon)
        return updated == 1
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