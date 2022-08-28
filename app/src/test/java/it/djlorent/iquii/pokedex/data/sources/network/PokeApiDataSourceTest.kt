package it.djlorent.iquii.pokedex.data.sources.network

import it.djlorent.iquii.pokedex.data.sources.network.api.PokemonService
import it.djlorent.iquii.pokedex.data.sources.network.api.model.*
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
            PokemonBaseResponse(
                name = "bulbasaur",
                url = "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            PokemonBaseResponse(
                name = "ivysaur",
                url = "https://pokeapi.co/api/v2/pokemon/2/"
            ),
            PokemonBaseResponse(
                name = "venusaur",
                url = "https://pokeapi.co/api/v2/pokemon/3/"
            ),
            PokemonBaseResponse(
                name = "charmander",
                url = "https://pokeapi.co/api/v2/pokemon/4/"
            ),
            PokemonBaseResponse(
                name = "charmeleon",
                url = "https://pokeapi.co/api/v2/pokemon/5/"
            ),
            PokemonBaseResponse(
                name = "charizard",
                url = "https://pokeapi.co/api/v2/pokemon/6/"
            ),
            PokemonBaseResponse(
                name = "squirtle",
                url = "https://pokeapi.co/api/v2/pokemon/7/"
            ),
            PokemonBaseResponse(
                name = "wartortle",
                url = "https://pokeapi.co/api/v2/pokemon/8/"
            ),
            PokemonBaseResponse(
                name = "blastoise",
                url = "https://pokeapi.co/api/v2/pokemon/9/"
            ),
            PokemonBaseResponse(
                name = "caterpie",
                url = "https://pokeapi.co/api/v2/pokemon/10/"
            ),
            PokemonBaseResponse(
                name = "metapod",
                url = "https://pokeapi.co/api/v2/pokemon/11/"
            ),
            PokemonBaseResponse(
                name = "butterfree",
                url = "https://pokeapi.co/api/v2/pokemon/12/"
            ),
            PokemonBaseResponse(
                name = "weedle",
                url = "https://pokeapi.co/api/v2/pokemon/13/"
            ),
            PokemonBaseResponse(
                name = "kakuna",
                url = "https://pokeapi.co/api/v2/pokemon/14/"
            ),
            PokemonBaseResponse(
                name = "beedrill",
                url = "https://pokeapi.co/api/v2/pokemon/15/"
            ),
            PokemonBaseResponse(
                name = "pidgey",
                url = "https://pokeapi.co/api/v2/pokemon/16/"
            ),
            PokemonBaseResponse(
                name = "pidgeotto",
                url = "https://pokeapi.co/api/v2/pokemon/17/"
            ),
            PokemonBaseResponse(
                name = "pidgeot",
                url = "https://pokeapi.co/api/v2/pokemon/18/"
            ),
            PokemonBaseResponse(
                name = "rattata",
                url = "https://pokeapi.co/api/v2/pokemon/19/"
            ),
            PokemonBaseResponse(
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

    private val pokemonDetailsMock =
        PokemonDetailsResponse(1, "bulbasaur",
        stats = listOf(
            PokemonBaseStat(
                baseStat =  45,
                effort = 0,
                stat = ModelBase("hp", "https://pokeapi.co/api/v2/stat/1/")
            ),
            PokemonBaseStat(
                baseStat = 49,
                effort = 0,
                stat = ModelBase("attack", "https://pokeapi.co/api/v2/stat/2/")
            ),
            PokemonBaseStat(
                baseStat = 49,
                effort = 0,
                stat = ModelBase("defense", "https://pokeapi.co/api/v2/stat/3/")
            ),
            PokemonBaseStat(
                baseStat = 65,
                effort = 1,
                stat = ModelBase("special-attack", "https://pokeapi.co/api/v2/stat/4/")
            ),
            PokemonBaseStat(
                baseStat = 65,
                effort = 0,
                stat = ModelBase("special-defense", "https://pokeapi.co/api/v2/stat/5/")
            ),
            PokemonBaseStat(
                baseStat = 45,
                effort = 0,
                stat = ModelBase("speed", "https://pokeapi.co/api/v2/stat/6/")
            )
        ),
        types = listOf(
            PokemonBaseType(
                slot = 1,
                type = ModelBase("grass", "https://pokeapi.co/api/v2/type/12/")
            ),
            PokemonBaseType(
                slot = 2,
                type = ModelBase("poison","https://pokeapi.co/api/v2/type/4/")
            )
        )
    )

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
        assert(result.stats != null)
        assert(result.types != null)
        assert(result.types!!.isNotEmpty())
        assert(result.stats!!.hp > 0)
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