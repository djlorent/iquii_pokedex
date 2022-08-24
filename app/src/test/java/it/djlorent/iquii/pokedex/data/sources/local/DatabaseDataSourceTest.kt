package it.djlorent.iquii.pokedex.data.sources.local

import it.djlorent.iquii.pokedex.data.sources.local.database.PokeDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.FavoritesDao
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.PokedexDao
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.models.Pokemon
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

    private val pokedexMock = listOf(
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

    private val pokedexDbMock = listOf(
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
        Mockito
            .`when`(pokedexDao.getAll())
            .thenReturn(pokedexDbMock)

        val result = localDataSource.getPokedex()
        result.forEach(::println)

        assert(result.isNotEmpty())
        assert(result.size == pokedexDbMock.size)
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
            .thenReturn(pokedexDbMock.subList(0, 10))

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
            .thenReturn(pokedexDbMock.subList(10, 20))

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
            .`when`(pokedexDao.insert(pokedexDbMock.subList(0, 10)))
            .thenReturn(listOf(0,1,2,3,4,5,6,7,8,9))

        val result = localDataSource.addToPokedex(pokedexMock.subList(0, 10))

        assert(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingFavorites() = runTest {
        Mockito
            .`when`(favoritesDao.getAll())
            .thenReturn(pokedexDbMock.subList(5, 10))

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
            .thenReturn(pokedexDbMock.subList(5, 10))

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
            .thenReturn(pokedexDbMock.find { it.id == pokemonIdMock })

        val result = localDataSource.getPokemonDetails(pokemonIdMock)

        assert(result != null)
        assert(result!!.id == pokemonIdMock)
        assert(result.name == pokedexMock.find { it.id == pokemonIdMock }!!.name)
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