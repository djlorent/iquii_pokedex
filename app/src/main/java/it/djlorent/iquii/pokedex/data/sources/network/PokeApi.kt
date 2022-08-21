package it.djlorent.iquii.pokedex.data.sources.network

import javax.inject.Inject

class PokeApi @Inject constructor(
    val pokemonService: PokemonService
) {

    companion object {
        const val baseUri = "https://pokeapi.co/api/v2/"
    }
}