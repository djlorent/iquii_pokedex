package it.djlorent.iquii.pokedex

object Constants {

    const val SELECTED_POKEMON_ID = "pokemonId"

    const val BASE_URI = "https://pokeapi.co/api/v2/"

    fun getImageUrl(pokemonId: Int) =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"
}