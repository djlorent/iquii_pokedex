package it.djlorent.iquii.pokedex.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 1
    private val _pageFlow = MutableStateFlow(currentPage)

    private val _pokedexUiStateFlow = MutableStateFlow(PokedexUiState(true, false))
    val pokedexUiStateFlow = _pokedexUiStateFlow.asStateFlow()

    val favoritesFlow = repository.getAllFavoriteIds()

    @OptIn(FlowPreview::class)
    val pokeStateFlow: Flow<List<PokemonState>> =
        _pageFlow
            .map { page ->
                getPagingPokedex(page)
                    .map{ pokedexPage ->
                        pokedexPage.map {
                            PokemonState(it, favoritesFlow.first().contains(it.id))
                        }
                    }
                    .catch { println(it.message) }
                    .onEmpty { _pokedexUiStateFlow.value = PokedexUiState(false, true) }
                    .onCompletion { _pokedexUiStateFlow.update { it.copy(isLoading = false) } }
            }
            .flattenConcat()

    private fun getPagingPokedex(page: Int): Flow<List<Pokemon>> = flow {
        val pokedexPage = if(page < currentPage){
            repository.getPokedex(page, Constants.PAGE_SIZE * currentPage)
        } else {
            repository.getPokedex(page, Constants.PAGE_SIZE)
        }

        if(pokedexPage.isNotEmpty())
            emit(pokedexPage)
    }

    suspend fun addOrRemoveToFavorite(pokemonState: PokemonState): FavoriteManagerResult {
        return viewModelScope.async {
            if(pokemonState.isFavorite){
                val result = repository.removeFavoritePokemon(pokemonState.pokemon.id)
                return@async if(result) FavoriteManagerResult.Removed else FavoriteManagerResult.RemoveError
            }else{
                val result = repository.addFavoritePokemon(pokemonState.pokemon.id)
                return@async if(result) FavoriteManagerResult.Added else FavoriteManagerResult.AddError
            }
        }.await()
    }

    fun requestNewPage() {
        _pokedexUiStateFlow.update { it.copy(isLoading = true) }
        _pageFlow.value = ++currentPage
    }

    fun resetPage() {
        _pageFlow.value = 1
    }
}

