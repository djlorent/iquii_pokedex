package it.djlorent.iquii.pokedex.mappers

import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonType as LocalPokemonType
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonDetailsResponse
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonBaseResponse
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.models.PokemonStats
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon as LocalPokemon
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonDetails as LocalPokemonDetails
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.PokemonStats as LocalPokemonStats
import it.djlorent.iquii.pokedex.models.PokemonType

object PokemonMapper {

    fun fromNetwork(netPokemon: PokemonBaseResponse): Pokemon {
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
            image = Constants.getImageUrl(netPokemon.id),
            types = netPokemon.types.sortedBy { it.slot }.map { PokemonType.valueOf(it.type.name) },
            stats = PokemonStats(
                hp = netPokemon.stats.first { it.stat.name == PokemonStats::hp.name}.baseStat,
                attack = netPokemon.stats.first { it.stat.name == PokemonStats::attack.name }.baseStat,
                defense = netPokemon.stats.first { it.stat.name == PokemonStats::defense.name }.baseStat,
                specialAttack = netPokemon.stats.first { it.stat.name == "special-attack" }.baseStat,
                specialDefense = netPokemon.stats.first { it.stat.name == "special-defense" }.baseStat,
                speed = netPokemon.stats.first { it.stat.name == PokemonStats::speed.name }.baseStat,
            )
        )
    }

    fun fromLocal(localPokemon: LocalPokemon): Pokemon {
        return Pokemon(
            id = localPokemon.id,
            name = localPokemon.name,
            image = Constants.getImageUrl(localPokemon.id)
        )
    }

    fun fromLocalDetails(localPokemon: LocalPokemonDetails): Pokemon {
        return Pokemon(
            id = localPokemon.pokemon.id,
            name = localPokemon.pokemon.name,
            image = Constants.getImageUrl(localPokemon.pokemon.id),
            stats = localPokemon.stats?.let {
                PokemonStats(it.hp, it.attack, it.defense, it.specialAttack, it.specialDefense, it.speed)
            },
            types = localPokemon.types.map { PokemonType.valueOf(it.type) }
        )
    }

    fun toLocal(pokemon: Pokemon): LocalPokemon {
        return LocalPokemon(
            id = pokemon.id,
            name = pokemon.name
        )
    }

    fun toLocalDetails(pokemon: Pokemon): LocalPokemonDetails {
        return LocalPokemonDetails(
            pokemon = LocalPokemon(pokemon.id, pokemon.name),
            stats = pokemon.stats!!.let {
                LocalPokemonStats(pokemon.id, it.hp, it.attack, it.defense, it.specialAttack, it.specialDefense, it.speed)
            },
            types = pokemon.types!!.map { LocalPokemonType(pokemon.id, it.name) }
        )
    }
}