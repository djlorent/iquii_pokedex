package it.djlorent.iquii.pokedex.data.sources.local.database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
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

        favoritesDao.deleteById(1)

        val favorites = favoritesDao.getAllIds()
        favorites.test {
            val items = awaitItem()
            assert(items.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun getAllFavorites() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        val favorites = favoritesDao.getAllIds()
        favorites.test {
            val items = awaitItem()
            items.forEach(::println)
            assert(items.isNotEmpty())
            assert(items.size == 3)
            assert(items.first() == 2)
            awaitComplete()
        }
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
    fun deletePokemonById() = runTest {
        favoritesDao.insert(listOf(
            Favorite(pokemonId = 2),
            Favorite(pokemonId = 3),
            Favorite(pokemonId = 5),
        ))

        val result = favoritesDao.deleteById(5)

        assert(result == 1)
    }
}