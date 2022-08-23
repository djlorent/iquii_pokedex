package it.djlorent.iquii.pokedex

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.djlorent.iquii.pokedex.data.sources.network.PokeApi
import it.djlorent.iquii.pokedex.data.sources.network.PokemonService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonServiceTest {
    lateinit var service: PokemonService

    @BeforeClass
    fun setup() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(PokeApi.baseUri)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val pokeApi = PokeApi(retrofit.create(PokemonService::class.java))
        service = pokeApi.pokemonService
    }

    @Test
    fun calling_FirstPage_PokemonService() = runTest {
        val response = service.fetchPokemons(20,0)
        assert(response.body() != null)
        assert(response.body()!!.totalCount > 0)
        assert(response.body()!!.previous == null)
        assert(response.body()!!.next != null)
        response.body()?.results?.forEach(::println)
    }

    @Test
    fun calling_ThirdPage_PokemonService() = runTest {
        val response = service.fetchPokemons(20,40)
        assert(response.body() != null)
        assert(response.body()!!.totalCount > 0)
        assert(response.body()!!.previous != null)
        assert(response.body()!!.next != null)
        response.body()?.results?.forEach(::println)
    }

    @Test
    fun calling_LastPage_PokemonService() = runTest {
        val response = service.fetchPokemons(20,1150)
        assert(response.body() != null)
        assert(response.body()!!.totalCount > 0)
        assert(response.body()!!.previous != null)
        assert(response.body()!!.next == null)
        response.body()?.results?.forEach(::println)
    }

    @Test
    fun calling_PokemonByName_PokemonService() = runTest {
        val response = service.fetchPokemon("blastoise")

        assert(response.body() != null)
        assert(response.body()!!.id == 9)
        assert(response.body()!!.name == "blastoise")

        println(response.body()?.toString())
    }

    @Test
    fun calling_PokemonById_PokemonService() = runTest {
        val response = service.fetchPokemon("9")

        assert(response.body() != null)
        assert(response.body()!!.id == 9)
        assert(response.body()!!.name == "blastoise")

        println(response.body()?.toString())
    }

}