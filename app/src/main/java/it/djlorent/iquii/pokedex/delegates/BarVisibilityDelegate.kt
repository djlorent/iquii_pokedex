package it.djlorent.iquii.pokedex.delegates

import androidx.lifecycle.LifecycleOwner

interface BarVisibilityDelegate {
    fun registerBarVisibility(owner: LifecycleOwner, set: Set<Int>)
}

