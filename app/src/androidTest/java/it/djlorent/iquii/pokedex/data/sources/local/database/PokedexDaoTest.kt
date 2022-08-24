package it.djlorent.iquii.pokedex.data.sources.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.PokedexDao
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class PokedexDaoTest {

    private lateinit var pokeDatabase: PokeDatabase
    private lateinit var pokedexDao: PokedexDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pokeDatabase = Room.inMemoryDatabaseBuilder(context, PokeDatabase::class.java).build()
        pokedexDao = pokeDatabase.pokedexDao()
    }

    @After
    fun teardown() {
        pokeDatabase.close()
    }

    @Test
    fun insertPokemon() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        val byName = pokedexDao.getByName(pokemon.name)

        assert(byName != null)
        assert(byName?.name == pokemon.name)
        assert(byName?.id == pokemon.id)
    }

    @Test
    fun deletePokemon() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        var pokedex = pokedexDao.getAll()
        assert(pokedex.isNotEmpty())

        pokedexDao.delete(pokemon)

        pokedex = pokedexDao.getAll()
        assert(pokedex.isEmpty())
    }

    @Test
    fun updatePokemon() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        val newPokemon = pokemon.copy(name = "squirtle")
        pokedexDao.update(newPokemon)

        val pokedex = pokedexDao.getAll()
        pokedex.forEach(::println)

        assert(pokedex.count() == 1)
        assert(pokedex.contains(newPokemon))
        assert(!pokedex.contains(pokemon))
    }

    @Test
    fun getAllPokemon() = runTest {
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

        assert(pokedex.count() == 6)
    }

    @Test
    fun getAllPokemon_FirstPage() = runTest {
        pokedexDao.insert(listOf(
            Pokemon(1, "bulbasaur"),
            Pokemon(2, "ivysaur"),
            Pokemon(3, "venusaur"),
            Pokemon(4, "charmander"),
            Pokemon(5, "charmeleon"),
            Pokemon(6, "charizard"),
        ))

        val pokedex = pokedexDao.getAll(0, 2)
        pokedex.forEach(::println)

        assert(pokedex.count() == 2)
        assert(pokedex[0].id == 1)
        assert(pokedex[0].name == "bulbasaur")
        assert(pokedex[1].id == 2)
        assert(pokedex[1].name == "ivysaur")
    }

    @Test
    fun getAllPokemon_SecondPage() = runTest {
        pokedexDao.insert(listOf(
            Pokemon(1, "bulbasaur"),
            Pokemon(2, "ivysaur"),
            Pokemon(3, "venusaur"),
            Pokemon(4, "charmander"),
            Pokemon(5, "charmeleon"),
            Pokemon(6, "charizard"),
        ))

        val pokedex = pokedexDao.getAll(1, 2)
        pokedex.forEach(::println)

        assert(pokedex.count() == 2)
        assert(pokedex[0].id == 3)
        assert(pokedex[0].name == "venusaur")
        assert(pokedex[1].id == 4)
        assert(pokedex[1].name == "charmander")
    }

    @Test
    fun getPokemonById() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        val byId = pokedexDao.getById(1)

        assert(byId != null)
        assert(byId?.name == pokemon.name)
        assert(byId?.id == pokemon.id)
    }

    @Test
    fun getPokemonByName() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        val byName = pokedexDao.getByName("bulbasaur")

        assert(byName != null)
        assert(byName?.name == pokemon.name)
        assert(byName?.id == pokemon.id)
    }

    @Test
    fun deleteAllPokemon() = runTest {
        pokedexDao.insert(listOf(
            Pokemon(1, "bulbasaur"),
            Pokemon(2, "ivysaur"),
            Pokemon(3, "venusaur"),
            Pokemon(4, "charmander"),
            Pokemon(5, "charmeleon"),
            Pokemon(6, "charizard"),
        ))

        var pokedex = pokedexDao.getAll()
        pokedex.forEach(::println)
        assert(pokedex.isNotEmpty())

        pokedexDao.deleteAll()
        pokedex = pokedexDao.getAll()
        assert(pokedex.isEmpty())
    }

    @Test
    fun deletePokemonById() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        var pokedex = pokedexDao.getAll()
        assert(pokedex.isNotEmpty())

        pokedexDao.deleteById(1)

        pokedex = pokedexDao.getAll()
        assert(pokedex.isEmpty())
    }

    @Test
    fun deletePokemonByName() = runTest {
        val pokemon = Pokemon(1, "bulbasaur")
        pokedexDao.insert(pokemon)

        var pokedex = pokedexDao.getAll()
        assert(pokedex.isNotEmpty())

        pokedexDao.deleteByName("bulbasaur")

        pokedex = pokedexDao.getAll()
        assert(pokedex.isEmpty())
    }
}