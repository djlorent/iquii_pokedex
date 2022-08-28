package it.djlorent.iquii.pokedex.data.repositories

import app.cash.turbine.test
import it.djlorent.iquii.pokedex.data.sources.local.LocalDataSource
import it.djlorent.iquii.pokedex.data.sources.network.NetworkDataSource
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.utils.MockUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryTest {

    private lateinit var repository: PokemonRepository

    @Mock
    private lateinit var networkSrc: NetworkDataSource

    @Mock
    private lateinit var localSrc: LocalDataSource

    @Mock
    private lateinit var ioDispatcher: CoroutineDispatcher


    @Before
    fun setup() = runBlocking {
        MockitoAnnotations.openMocks(this)
        ioDispatcher = UnconfinedTestDispatcher()
        repository = PokemonRepositoryImpl(networkSrc, localSrc, ioDispatcher)
    }

    @Test
    fun gettingPokedex_FromLocal() = runTest {
        val pageSize = 5
        val page = 2

        val expected = MockUtils.pokedexMock().subList(pageSize * (page - 1), pageSize * page)
        println("Page: $page | PageSize: $pageSize | Indexs: ${expected.map { it.id }}")

        Mockito
            .`when`(localSrc.getPokedex(page, pageSize))
            .thenReturn(expected)

        val items = repository.getPokedex(page, pageSize)
        items.forEach(::println)
        Assert.assertEquals(expected, items)
    }

    @Test
    fun gettingPokedex_FromNetwork() = runTest {
        val pageSize = 5
        val page = 4

        val expected = MockUtils.pokedexMock().subList(pageSize * (page - 1), pageSize * page)
        println("Page: $page | PageSize: $pageSize | Indexs: ${expected.map { it.id }}")

        Mockito
            .`when`(localSrc.getPokedex(page, pageSize))
            .thenReturn(listOf())

        Mockito
            .`when`(networkSrc.fetchPokemons(pageSize, (page-1) * pageSize))
            .thenReturn(expected)

        val items = repository.getPokedex(page, pageSize)
        items.forEach(::println)
        Assert.assertEquals(expected, items)
    }

    @Test
    fun gettingFavorites_ThirdPage() = runTest {
        val pageSize = 5
        val page = 3

        val expected = MockUtils.pokedexMock().subList(pageSize * (page - 1), pageSize * page)
        println("Page: $page | PageSize: $pageSize | Indexs: ${expected.map { it.id }}")

        Mockito
            .`when`(localSrc.getFavorites(page, pageSize))
            .thenReturn(expected)

        val items = repository.getFavorites(page, pageSize)
        items.forEach(::println)
        Assert.assertEquals(expected, items)
    }

    @Test
    fun gettingPokemonInfo_FromLocal() = runTest {
        Mockito
            .`when`(localSrc.getPokemonDetails(1))
            .thenReturn(MockUtils.domainPokemonWithDetails())

        val item = repository.getPokemonInfo(1)
        assert(item?.id == 1)
        assert(item?.name == "bulbasaur")
    }

    @Test
    fun gettingPokemonInfo_FromNetwork() = runTest {
        Mockito
            .`when`(localSrc.getPokemonDetails(1))
            .thenReturn(MockUtils.domainPokemon())

        Mockito
            .`when`(localSrc.insertPokemonDetails(MockUtils.domainPokemonWithDetails()))
            .thenReturn(true)

        Mockito
            .`when`(networkSrc.fetchPokemon("1"))
            .thenReturn(MockUtils.domainPokemonWithDetails())

        val item = repository.getPokemonInfo(1)
        assert(item?.id == 1)
        assert(item?.name == "bulbasaur")
        assert(item?.stats != null)
        assert(item?.types != null)
    }

    @Test
    fun addingFavoritePokemon() = runTest {
        val id = 4
        Mockito
            .`when`(localSrc.addFavorite(id))
            .thenReturn(true)

        assert(repository.addFavoritePokemon(id))
    }

    @Test
    fun removingFavoritePokemon() = runTest {
        val id = 4
        Mockito
            .`when`(localSrc.removeFavorite(id))
            .thenReturn(true)

        assert(repository.removeFavoritePokemon(id))
    }

    suspend fun flowPaging(
        flow: (page: Int, pageSize: Int) -> Flow<List<Pokemon>>,
        mocks: suspend (page: Int, pageSize: Int) -> Unit
    ) {
        val pageSize = 5
        val totalPages = MockUtils.pokedexMock().size / pageSize

        for (page in 1..totalPages) {
            val expected = MockUtils.pokedexMock().subList(pageSize * (page - 1), pageSize * page)
            println("Page: $page | PageSize: $pageSize | Indexs: ${expected.map { it.id }}")

            mocks(page, pageSize)

            flow(page, pageSize).test {
                val items = awaitItem()
                items.forEach(::println)
                Assert.assertEquals(expected, items)
                awaitComplete()
            }
        }
    }
}