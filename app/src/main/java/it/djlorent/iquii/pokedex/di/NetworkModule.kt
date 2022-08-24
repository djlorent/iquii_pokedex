package it.djlorent.iquii.pokedex.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.sources.network.NetworkDataSource
import it.djlorent.iquii.pokedex.data.sources.network.PokeApiDataSource
import it.djlorent.iquii.pokedex.data.sources.network.api.PokemonService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val TIMEOUT_SECONDS: Long = 15

    @Provides
    @Singleton
    fun providesNetworkDataSource(pokemonService: PokemonService): NetworkDataSource =
        PokeApiDataSource(pokemonService)

    @Provides
    @Singleton
    fun provideCommonHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient()
            .newBuilder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideCommonRetrofitBuilder(
        moshi: Moshi
    ): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        httpClientBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder
            .baseUrl(Constants.BASE_URI)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonService(retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)
}