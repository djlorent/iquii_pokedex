package it.djlorent.iquii.pokedex.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.djlorent.iquii.pokedex.Constants
import it.djlorent.iquii.pokedex.data.repositories.PokemonRepository
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var _currentPage = 0
    private val _pageStateFlow = MutableStateFlow(1)
    private val _pokeList = mutableListOf<PokemonState>()
    val favoritesFlow = repository.getAllFavoriteIds()

    private val _pokedexUiStateFlow = MutableStateFlow(PokedexUiState(true, false))
    val pokedexUiStateFlow = _pokedexUiStateFlow.asStateFlow()

    val pokeStateLive: LiveData<List<PokemonState>> = _pageStateFlow
        .map { page -> getPagingPokedex(page) }
        .catch { println(it.message) }
        .asLiveData()

    private suspend fun getPagingPokedex(page: Int): List<PokemonState> {
        if(page > _currentPage){
            val newPageData =  repository.getPokedex(page, Constants.PAGE_SIZE).map {
                PokemonState(it, favoritesFlow.first().contains(it.id))
            }

            if(newPageData.isEmpty()){
                _pokedexUiStateFlow.value = PokedexUiState(false, true)
            }
            else {
                _currentPage++
                _pokeList.addAll(newPageData)
                _pokedexUiStateFlow.update { it.copy(isLoading = false) }
            }
        }
        return _pokeList.toList()
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
        _pageStateFlow.value++
    }
}

