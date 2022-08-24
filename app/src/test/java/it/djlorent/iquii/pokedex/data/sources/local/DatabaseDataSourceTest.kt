package it.djlorent.iquii.pokedex.data.sources.local

import it.djlorent.iquii.pokedex.data.sources.local.database.PokeDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.FavoritesDao
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.PokedexDao
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.utils.MockUtils
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon as LocalPokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DatabaseDataSourceTest {

    @Mock
    lateinit var pokeDatabase: PokeDatabase

    @Mock
    lateinit var pokedexDao: PokedexDao

    @Mock
    lateinit var favoritesDao: FavoritesDao

    lateinit var localDataSource: DatabaseDataSource

    @Before
    fun setup() = runBlocking {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(pokeDatabase.pokedexDao()).thenReturn(pokedexDao)
        Mockito.`when`(pokeDatabase.favoritesDao()).thenReturn(favoritesDao)

        localDataSource = DatabaseDataSource(pokeDatabase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingPokedex() = runTest {
        val  dbMock = MockUtils.pokedexDbMock()
        Mockito
            .`when`(pokedexDao.getAll())
            .thenReturn(dbMock)

        val result = localDataSource.getPokedex()
        result.forEach(::println)

        assert(result.isNotEmpty())
        assert(result.size == dbMock.size)
        assert(result.first().id == 1)
        assert(result.last().id == 20)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingPokedex_FirstPage() = runTest {
        val page = 1
        val pageSize = 10

        Mockito
            .`when`(pokedexDao.getAll(page,pageSize))
            .thenReturn(MockUtils.pokedexDbMock().subList(0, 10))

        val result = localDataSource.getPokedex(page, pageSize)
        result.forEach(::println)

        assert(result.isNotEmpty())
        assert(result.size == pageSize)
        assert(result.first().id == 1)
        assert(result.last().id == 10)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingPokedex_LastPage() = runTest {
        val page = 2
        val pageSize = 10

        Mockito
            .`when`(pokedexDao.getAll(page,pageSize))
            .thenReturn(MockUtils.pokedexDbMock().subList(10, 20))

        val result = localDataSource.getPokedex(page, pageSize)
        result.forEach(::println)

        assert(result.isNotEmpty())
        assert(result.size == pageSize)
        assert(result.first().id == 11)
        assert(result.last().id == 20)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addingToPokedex() = runTest {
        Mockito
            .`when`(pokedexDao.insert(MockUtils.pokedexDbMock().subList(0, 10)))
            .thenReturn(listOf(0,1,2,3,4,5,6,7,8,9))

        val result = localDataSource.addToPokedex(MockUtils.pokedexMock().subList(0, 10))

        assert(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingFavorites() = runTest {
        Mockito
            .`when`(favoritesDao.getAll())
            .thenReturn(MockUtils.pokedexDbMock().subList(5, 10))

        val result = localDataSource.getFavorites()
        result.forEach(::println)

        assert(result.isNotEmpty())
        assert(result.size == 5)
        assert(result.first().id == 6)
        assert(result.last().id == 10)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingFavorites_SecondPage() = runTest {
        val page = 2
        val pageSize = 5

        Mockito
            .`when`(favoritesDao.getAll(page,pageSize))
            .thenReturn(MockUtils.pokedexDbMock().subList(5, 10))

        val result = localDataSource.getFavorites(page, pageSize)
        result.forEach(::println)

        assert(result.isNotEmpty())
        assert(result.size == pageSize)
        assert(result.first().id == 6)
        assert(result.last().id == 10)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingPokemonDetails() = runTest {
        val pokemonIdMock = 3
        Mockito
            .`when`(pokedexDao.getById(pokemonIdMock))
            .thenReturn(MockUtils.pokedexDbMock().find { it.id == pokemonIdMock })

        val result = localDataSource.getPokemonDetails(pokemonIdMock)

        assert(result != null)
        assert(result!!.id == pokemonIdMock)
        assert(result.name == MockUtils.pokedexMock().find { it.id == pokemonIdMock }!!.name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addingFavorite() = runTest {
        val pokemonId = 7
        val favoriteMock = Favorite(pokemonId = pokemonId)
        Mockito
            .`when`(favoritesDao.insert(favoriteMock))
            .thenReturn(4)

        val result = localDataSource.addFavorite(pokemonId)

        assert(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun removingFavorite() = runTest {
        val pokemonId = 7

        Mockito
            .`when`(favoritesDao.deleteById(pokemonId))
            .thenReturn(1)

        val result = localDataSource.removeFavorite(pokemonId)

        assert(result)
    }
}