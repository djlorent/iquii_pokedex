package it.djlorent.iquii.pokedex.utils

import it.djlorent.iquii.pokedex.models.Pokemon

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
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "bulbasaur",
            id = 1
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "ivysaur",
            id = 2
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "venusaur",
            id = 3
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "charmander",
            id = 4
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "charmeleon",
            id = 5
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "charizard",
            id = 6
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "squirtle",
            id = 7
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "wartortle",
            id = 8
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "blastoise",
            id = 9
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "caterpie",
            id = 10
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "metapod",
            id = 11
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "butterfree",
            id = 12
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "weedle",
            id = 13
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "kakuna",
            id = 14
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "beedrill",
            id = 15
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "pidgey",
            id = 16
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "pidgeotto",
            id = 17
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "pidgeot",
            id = 18
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "rattata",
            id = 19
        ),
        it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon(
            name = "raticate",
            id = 20
        ),
    )
}