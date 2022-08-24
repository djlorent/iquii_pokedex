package it.djlorent.iquii.pokedex.data.sources.network

import it.djlorent.iquii.pokedex.data.sources.network.api.PokemonService
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonDetailsResponse
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonResponse
import it.djlorent.iquii.pokedex.data.sources.network.api.model.PokemonsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PokeApiDataSourceTest {

    @Mock
    lateinit var pokemonService: PokemonService

    lateinit var apiDataSource: PokeApiDataSource

    private val firstPageMock = PokemonsResponse(
        totalCount =  1154,
        next = "https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20",
        previous = null,
        results = listOf(
            PokemonResponse(
                name = "bulbasaur",
                url = "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            PokemonResponse(
                name = "ivysaur",
                url = "https://pokeapi.co/api/v2/pokemon/2/"
            ),
            PokemonResponse(
                name = "venusaur",
                url = "https://pokeapi.co/api/v2/pokemon/3/"
            ),
            PokemonResponse(
                name = "charmander",
                url = "https://pokeapi.co/api/v2/pokemon/4/"
            ),
            PokemonResponse(
                name = "charmeleon",
                url = "https://pokeapi.co/api/v2/pokemon/5/"
            ),
            PokemonResponse(
                name = "charizard",
                url = "https://pokeapi.co/api/v2/pokemon/6/"
            ),
            PokemonResponse(
                name = "squirtle",
                url = "https://pokeapi.co/api/v2/pokemon/7/"
            ),
            PokemonResponse(
                name = "wartortle",
                url = "https://pokeapi.co/api/v2/pokemon/8/"
            ),
            PokemonResponse(
                name = "blastoise",
                url = "https://pokeapi.co/api/v2/pokemon/9/"
            ),
            PokemonResponse(
                name = "caterpie",
                url = "https://pokeapi.co/api/v2/pokemon/10/"
            ),
            PokemonResponse(
                name = "metapod",
                url = "https://pokeapi.co/api/v2/pokemon/11/"
            ),
            PokemonResponse(
                name = "butterfree",
                url = "https://pokeapi.co/api/v2/pokemon/12/"
            ),
            PokemonResponse(
                name = "weedle",
                url = "https://pokeapi.co/api/v2/pokemon/13/"
            ),
            PokemonResponse(
                name = "kakuna",
                url = "https://pokeapi.co/api/v2/pokemon/14/"
            ),
            PokemonResponse(
                name = "beedrill",
                url = "https://pokeapi.co/api/v2/pokemon/15/"
            ),
            PokemonResponse(
                name = "pidgey",
                url = "https://pokeapi.co/api/v2/pokemon/16/"
            ),
            PokemonResponse(
                name = "pidgeotto",
                url = "https://pokeapi.co/api/v2/pokemon/17/"
            ),
            PokemonResponse(
                name = "pidgeot",
                url = "https://pokeapi.co/api/v2/pokemon/18/"
            ),
            PokemonResponse(
                name = "rattata",
                url = "https://pokeapi.co/api/v2/pokemon/19/"
            ),
            PokemonResponse(
                name = "raticate",
                url = "https://pokeapi.co/api/v2/pokemon/20/"
            ),
        )
    )

    private val emptyPageMock = PokemonsResponse(
        totalCount =  1154,
        next = null,
        previous = "https://pokeapi.co/api/v2/pokemon/?offset=1200&limit=20",
        results = listOf()
    )

    private val pokemonDetailsMock = PokemonDetailsResponse(1, "bulbasaur")

    @Before
    fun setup() = runBlocking {
        MockitoAnnotations.openMocks(this)
        apiDataSource = PokeApiDataSource(pokemonService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchingPokemonList_FirstPage() = runTest {
        Mockito
            .`when`(pokemonService.fetchPokemons(20, 0))
            .thenReturn(Response.success(firstPageMock))

        val result = apiDataSource.fetchPokemons(20, 0)
        result.forEach(::println)
        assert(result.isNotEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchingPokemonList_Empty() = runTest {
        Mockito
            .`when`(pokemonService.fetchPokemons(20, 1200))
            .thenReturn(Response.success(emptyPageMock))

        val result = apiDataSource.fetchPokemons(20, 1200)
        assert(result.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchingPokemonDetails() = runTest {
        Mockito
            .`when`(pokemonService.fetchPokemon("1"))
            .thenReturn(Response.success(pokemonDetailsMock))

        val result = apiDataSource.fetchPokemon("1" )

        assert(result != null)
        assert(result!!.id == 1)
        assert(result.name == "bulbasaur")
        assert(result.image == "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchingPokemonDetails_NotFound() = runTest {
        Mockito
            .`when`(pokemonService.fetchPokemon("1"))
            .thenReturn(Response.error(404, ResponseBody.create(null, "")))

        val result = apiDataSource.fetchPokemon("1" )

        assert(result == null)
    }
}