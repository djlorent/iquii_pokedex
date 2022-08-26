package it.djlorent.iquii.pokedex.ui.pokedex

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.models.Pokemon
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 1
    private val _pageFlow = MutableStateFlow(currentPage)

    private val _pokedexUiStateFlow = MutableStateFlow(PokedexUiState(true, false))
    val pokedexUiStateFlow = _pokedexUiStateFlow.asStateFlow()

    @OptIn(FlowPreview::class)
    val pokeStateFlow: Flow<List<Pokemon>> =
        _pageFlow
            .map { page ->
                getPagingPokedex(page)
                    .onEmpty { _pokedexUiStateFlow.value = PokedexUiState(false, true) }
                    .onCompletion { _pokedexUiStateFlow.update { it.copy(isLoading = false) } }
            }
            .flattenConcat()

    private fun getPagingPokedex(page: Int): Flow<List<Pokemon>> = flow {
        val pokedexPage = if(page < currentPage){
            repository.getPokedex(page, 20 * currentPage)
        } else {
            repository.getPokedex(page, 20)
        }

        if(pokedexPage.isNotEmpty())
            emit(pokedexPage)
    }

    fun requestNewPage() {
        _pokedexUiStateFlow.update { it.copy(isLoading = true) }
        _pageFlow.value = ++currentPage
    }

    fun resetPage() {
        _pageFlow.value = 1
    }
}

data class PokedexUiState(
    var isLoading: Boolean = false,
    var isComplete: Boolean = false
)

