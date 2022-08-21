package it.djlorent.iquii.pokedex.data.sources.network

import it.djlorent.iquii.pokedex.data.sources.network.model.PokemonResponse
import it.djlorent.iquii.pokedex.data.sources.network.model.PokemonsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : Response<PokemonsResponse>

    @GET("pokemon/{idOrName}")
    suspend fun fetchPokemon(
        @Path("idOrName") idOrName: String
    ): Response<PokemonResponse>
}