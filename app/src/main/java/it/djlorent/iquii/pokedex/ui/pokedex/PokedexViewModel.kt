package it.djlorent.iquii.pokedex.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PokedexViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is pokedex Fragment"
    }
    val text: LiveData<String> = _text
}