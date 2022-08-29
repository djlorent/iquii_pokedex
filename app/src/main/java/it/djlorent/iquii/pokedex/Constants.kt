package it.djlorent.iquii.pokedex

object Constants {

    const val SELECTED_POKEMON_ID = "pokemonId"
    const val SELECTED_POKEMON_NAME = "pokemonName"
    const val PAGE_SIZE = 50
    const val PAGINATION_THRESHOLD = 20

    const val BASE_URI = "https://pokeapi.co/api/v2/"

    fun getImageUrl(pokemonId: Int) =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"
}