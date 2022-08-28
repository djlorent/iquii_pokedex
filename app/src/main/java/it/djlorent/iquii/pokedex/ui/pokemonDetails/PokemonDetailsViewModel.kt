package it.djlorent.iquii.pokedex.ui.pokemonDetails

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    state: SavedStateHandle
) : ViewModel() {

    private var _pokemonDetails = MutableLiveData<Pokemon>()
    val pokemonDetails = _pokemonDetails

    init {
        viewModelScope.launch {
            val pokemonId = state.get<Int>(Constants.SELECTED_POKEMON_ID)
            _pokemonDetails.value = repository.getPokemonInfo(pokemonId!!)
        }
    }
}

