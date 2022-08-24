package it.djlorent.iquii.pokedex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.djlorent.iquii.pokedex.data.sources.local.DatabaseDataSource
import it.djlorent.iquii.pokedex.data.sources.local.LocalDataSource
import it.djlorent.iquii.pokedex.data.sources.local.database.PokeDatabase
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.FavoritesDao
import it.djlorent.iquii.pokedex.data.sources.local.database.dao.PokedexDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(pokeDatabase: PokeDatabase): LocalDataSource =
        DatabaseDataSource(pokeDatabase)

    @Singleton
    @Provides
    fun providePokeDb(
        @ApplicationContext context: Context
    ): PokeDatabase {
        val db = Room.databaseBuilder(
            context.applicationContext,
            PokeDatabase::class.java,
            PokeDatabase.DATABASE_NAME)

        return db.build()
    }

    @Singleton
    @Provides
    fun providePokedexDAO(pokeDatabase: PokeDatabase): PokedexDao = pokeDatabase.pokedexDao()

    @Singleton
    @Provides
    fun provideFavoritesDAO(pokeDatabase: PokeDatabase): FavoritesDao = pokeDatabase.favoritesDao()
}