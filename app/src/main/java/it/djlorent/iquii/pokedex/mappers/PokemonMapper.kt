package it.djlorent.iquii.pokedex.mappers

import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonDetailsResponse
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonResponse
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon as LocalPokemon

object PokemonMapper {

    fun fromNetwork(netPokemon: PokemonResponse): Pokemon {
        val id = netPokemon.url
            .split('/').dropLast(1).last().toInt()

        return Pokemon(
            id = id,
            name = netPokemon.name,
            image = Constants.getImageUrl(id)
        )
    }

    fun fromNetwork(netPokemon: PokemonDetailsResponse): Pokemon {
        return Pokemon(
            id = netPokemon.id,
            name = netPokemon.name,
            image = Constants.getImageUrl(netPokemon.id)
        )
    }

    fun fromLocal(localPokemon: LocalPokemon): Pokemon {
        return Pokemon(
            id = localPokemon.id,
            name = localPokemon.name,
            image = Constants.getImageUrl(localPokemon.id)
        )
    }

    fun toLocal(pokemon: Pokemon): LocalPokemon {
        return LocalPokemon(
            id = pokemon.id,
            name = pokemon.name
        )
    }
}