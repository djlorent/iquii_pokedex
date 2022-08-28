package it.djlorent.iquii.pokedex

import androidx.navigation.NavController

interface BottomNavigationActivity {
    val navController: NavController

    fun setBottomNavigationVisibility(isVisible: Boolean)
}