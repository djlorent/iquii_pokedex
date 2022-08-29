package it.djlorent.iquii.pokedex.ui.pokemonDetails

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.di.DispatcherModule
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    @DispatcherModule.IoDispatcher
    ioDispatcher: CoroutineDispatcher,
    state: SavedStateHandle
) : ViewModel() {

    private var _pokemonDetails = MutableLiveData<Pokemon>()
    val pokemonDetails = _pokemonDetails

    init {
        viewModelScope.launch(ioDispatcher) {
            val pokemonId = state.get<Int>(Constants.SELECTED_POKEMON_ID)
            val pokemonName = state.get<String>(Constants.SELECTED_POKEMON_NAME)

            pokemonId?.let {
                //Load Main info while waiting repository response
                _pokemonDetails.postValue(Pokemon(pokemonId, pokemonName ?: "", Constants.getImageUrl(pokemonId)))

                _pokemonDetails.postValue(repository.getPokemonInfo(pokemonId))
            }
        }
    }
}

