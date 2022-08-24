package it.djlorent.iquii.pokedex.data.sources.local.database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.FavoritesDao
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Favorite
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {

    private lateinit var pokeDatabase: PokeDatabase
    private lateinit var favoritesDao: FavoritesDao

    @Before
    fun setup() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pokeDatabase = Room.inMemoryDatabaseBuilder(context, PokeDatabase::class.java).build()

        favoritesDao = pokeDatabase.favoritesDao()

        val pokedexDao = pokeDatabase.pokedexDao()
        pokedexDao.insert(listOf(
            Pokemon(1, "bulbasaur"),
            Pokemon(2, "ivysaur"),
            Pokemon(3, "venusaur"),
            Pokemon(4, "charmander"),
            Pokemon(5, "charmeleon"),
            Pokemon(6, "charizard"),
        ))

        val pokedex = pokedexDao.getAll()
        pokedex.forEach(::println)
    }

    @After
    fun teardown() {
        pokeDatabase.close()
    }

    @Test
    fun insertFavorite() = runTest {
        val favorite = Favorite(pokemonId = 3)
        favoritesDao.insert(favorite)

        val savedFavorite = favoritesDao.getById(3)

        assert(savedFavorite != null)
        assert(savedFavorite?.id == 3)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun insertFavorite_Exception() = runTest {
        val favorite = Favorite(pokemonId = 7)
        favoritesDao.insert(favorite)

        val savedFavorite = favoritesDao.getById(7)

        assert(savedFavorite == null)
    }

    @Test
    fun deleteFavorite() = runTest {
        val favorite = Favorite(pokemonId = 1)
        favoritesDao.insert(favorite)

        var favorites = favoritesDao.getAll()
        favorites.forEach(::println)
        assert(favorites.isNotEmpty())

        favoritesDao.deleteById(1)

        favorites = favoritesDao.getAll()
        assert(favorites.isEmpty())
    }

    @Test
    fun getAllFavorites() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        val favorites = favoritesDao.getAll()
        favorites.forEach(::println)

        assert(favorites.count() == 3)
    }

    @Test
    fun getAllFavorites_FirstPage() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        val favorites = favoritesDao.getAll(0, 1)
        favorites.forEach(::println)

        assert(favorites.count() == 1)
        assert(favorites[0].id == 2)
    }

    @Test
    fun getAllPokemon_SecondPage() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        val favorites = favoritesDao.getAll(1, 1)
        favorites.forEach(::println)

        assert(favorites.count() == 1)
        assert(favorites[0].id == 3)
    }

    @Test
    fun getPokemonById() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        val favorite = favoritesDao.getById(3)

        assert(favorite != null)
        assert(favorite?.id == 3)
    }

    @Test
    fun deleteAllPokemon() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        var pokedex = favoritesDao.getAll()
        pokedex.forEach(::println)
        assert(pokedex.isNotEmpty())

        favoritesDao.deleteAll()
        pokedex = favoritesDao.getAll()
        assert(pokedex.isEmpty())
    }

    @Test
    fun deletePokemonById() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        var favorites = favoritesDao.getAll()
        assert(favorites.isNotEmpty())

        favoritesDao.deleteById(5)

        favorites = favoritesDao.getAll()
        assert(favorites.count() == 2)
    }
}