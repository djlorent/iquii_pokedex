package it.djlorent.iquii.pokedex.data.sources.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.PokemonStatsDao
import it.djlorent.iquii.pokedex.data.sources.local.database.entities.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class PokemonStatsDaoTest {

    private lateinit var pokeDatabase: PokeDatabase
    private lateinit var pokemonStatsDao: PokemonStatsDao
    private val pokemonDetails = PokemonDetails(
        Pokemon(1, "bulbasaur"),
        stats = PokemonStats(1,45,49,49,65,65, 45),
        types = listOf(
            PokemonType(1, "grass"),
            PokemonType(1, "poison"),
        )
    )

    @Before
    fun setup(): Unit = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pokeDatabase = Room.inMemoryDatabaseBuilder(context, PokeDatabase::class.java).build()

        pokemonStatsDao = pokeDatabase.pokemonStatsDao()

        val pokedexDao = pokeDatabase.pokedexDao()
        pokedexDao.insert(Pokemon(1, "bulbasaur"))
    }

    @After
    fun teardown() {
        pokeDatabase.close()
    }

    @Test
    fun insertStats() = runTest {
        val result = pokemonStatsDao.insert(pokemonDetails.stats)
        assert(result > 0)
    }

    @Test
    fun getPokemonStatsById() = runTest {
        pokemonStatsDao.insert(pokemonDetails.stats)

        val result = pokemonStatsDao.getById(1)

        assert(result != null)
        assert(result!!.id == 1)
        assert(result.hp > 0)
    }

    @Test
    fun deletePokemonStatsById() = runTest {
        pokemonStatsDao.insert(pokemonDetails.stats)

        val pokDet = pokemonStatsDao.getById(1)
        assert(pokDet != null)

        val result = pokemonStatsDao.deleteById(1)
        assert(result == 1)
    }
}