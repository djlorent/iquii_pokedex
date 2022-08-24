package it.djlorent.iquii.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepositoryImpl
import it.djlorent.iquii.pokedex.data.sources.local.LocalDataSource
import it.djlorent.iquii.pokedex.data.sources.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun providesPokemonRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PokemonRepository =
        PokemonRepositoryImpl(networkDataSource, localDataSource, ioDispatcher)
}