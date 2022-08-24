package it.djlorent.iquii.pokedex.ui.pokedex

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel

@HiltViewModel

    private val _text = MutableLiveData<String>().apply {
        value = "This is pokedex Fragment"
    }
    val text: LiveData<String> = _text
}