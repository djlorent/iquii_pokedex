package it.djlorent.iquii.pokedex.ui.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import it.djlorent.iquii.pokedex.models.Pokemon

class PokemonState(
    val pokemon: Pokemon,
    var isFavorite: Boolean
): BaseObservable() {

    fun updateFavorite(favorite: Boolean){
        isFavorite = favorite
        notifyChange()
    }
}