package it.djlorent.iquii.pokedex.utils


import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonType
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon as LocalPokemon
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonDetails as LocalPokemonDetails
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonStats as LocalPokemonStats
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.models.PokemonStats

object MockUtils {
    fun pokedexMock() = listOf(
        Pokemon(
            name = "bulbasaur",
            id = 1,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
        ),
        Pokemon(
            name = "ivysaur",
            id = 2,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"
        ),
        Pokemon(
            name = "venusaur",
            id = 3,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"
        ),
        Pokemon(
            name = "charmander",
            id = 4,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"
        ),
        Pokemon(
            name = "charmeleon",
            id = 5,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png"
        ),
        Pokemon(
            name = "charizard",
            id = 6,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"
        ),
        Pokemon(
            name = "squirtle",
            id = 7,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"
        ),
        Pokemon(
            name = "wartortle",
            id = 8,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png"
        ),
        Pokemon(
            name = "blastoise",
            id = 9,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"
        ),
        Pokemon(
            name = "caterpie",
            id = 10,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png"
        ),
        Pokemon(
            name = "metapod",
            id = 11,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png"
        ),
        Pokemon(
            name = "butterfree",
            id = 12,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png"
        ),
        Pokemon(
            name = "weedle",
            id = 13,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png"
        ),
        Pokemon(
            name = "kakuna",
            id = 14,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png"
        ),
        Pokemon(
            name = "beedrill",
            id = 15,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png"
        ),
        Pokemon(
            name = "pidgey",
            id = 16,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/16.png"
        ),
        Pokemon(
            name = "pidgeotto",
            id = 17,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/17.png"
        ),
        Pokemon(
            name = "pidgeot",
            id = 18,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/18.png"
        ),
        Pokemon(
            name = "rattata",
            id = 19,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/19.png"
        ),
        Pokemon(
            name = "raticate",
            id = 20,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/20.png"
        ),
    )

    fun pokedexDbMock() = listOf(
        LocalPokemon(
            name = "bulbasaur",
            id = 1
        ),
        LocalPokemon(
            name = "ivysaur",
            id = 2
        ),
        LocalPokemon(
            name = "venusaur",
            id = 3
        ),
        LocalPokemon(
            name = "charmander",
            id = 4
        ),
        LocalPokemon(
            name = "charmeleon",
            id = 5
        ),
        LocalPokemon(
            name = "charizard",
            id = 6
        ),
        LocalPokemon(
            name = "squirtle",
            id = 7
        ),
        LocalPokemon(
            name = "wartortle",
            id = 8
        ),
        LocalPokemon(
            name = "blastoise",
            id = 9
        ),
        LocalPokemon(
            name = "caterpie",
            id = 10
        ),
        LocalPokemon(
            name = "metapod",
            id = 11
        ),
        LocalPokemon(
            name = "butterfree",
            id = 12
        ),
        LocalPokemon(
            name = "weedle",
            id = 13
        ),
        LocalPokemon(
            name = "kakuna",
            id = 14
        ),
        LocalPokemon(
            name = "beedrill",
            id = 15
        ),
        LocalPokemon(
            name = "pidgey",
            id = 16
        ),
        LocalPokemon(
            name = "pidgeotto",
            id = 17
        ),
        LocalPokemon(
            name = "pidgeot",
            id = 18
        ),
        LocalPokemon(
            name = "rattata",
            id = 19
        ),
        LocalPokemon(
            name = "raticate",
            id = 20
        ),
    )

    fun domainPokemon() = Pokemon(1,"bulbasaur")
    fun dbPokemon() = LocalPokemon(1,"bulbasaur")

    fun domainPokemonWithDetails() = Pokemon(
        1,
        "bulbasaur",
         stats = PokemonStats(45,49,49,65,65, 45),
         types = listOf("grass","poison")
    )

    fun dbPokemonWithDetails() = LocalPokemonDetails(
        pokemon = LocalPokemon(1, "bulbasaur"),
        stats = LocalPokemonStats(1,45,49,49,65,65, 45),
        types = listOf(
            PokemonType(1, "grass"),
            PokemonType(1, "poison")
        )
    )

}