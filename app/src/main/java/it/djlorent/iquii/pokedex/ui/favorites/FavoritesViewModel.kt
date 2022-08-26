package it.djlorent.iquii.pokedex.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import it.djlorent.iquii.pokedex.ui.pokedex.PokedexUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _favoritesUiStateFlow = MutableStateFlow(PokedexUiState(true, false))
    val favoritesUiStateFlow = _favoritesUiStateFlow.asStateFlow()

    val favoritesFlow: Flow<List<Pokemon>> =
        repository.getAllFavorites()
            .catch {
                println(it.message)
            }
            .onEmpty {
                _favoritesUiStateFlow.value = PokedexUiState(false, true)
            }
            .onCompletion {
                _favoritesUiStateFlow.update { it.copy(isLoading = false) }
            }
            .onEach {
                _favoritesUiStateFlow.update { it.copy(isLoading = false) }
            }

    suspend fun removeFromFavorite(pokemon: Pokemon): FavoriteManagerResult {
        return viewModelScope.async {
            val result = repository.removeFavoritePokemon(pokemon.id)
            return@async if(result) FavoriteManagerResult.Removed else FavoriteManagerResult.RemoveError
        }.await()
    }
}