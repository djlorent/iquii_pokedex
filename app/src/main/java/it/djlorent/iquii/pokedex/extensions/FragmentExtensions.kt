package it.djlorent.iquii.pokedex.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

fun Fragment.subscribeOnStarted(subscriber : suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch{
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            subscriber()
        }
    }
}

fun Fragment.navigateToWithSharedView(direction: NavDirections, sharedView: View){
    try {
        findNavController().navigate(
            direction,
            FragmentNavigator.Extras.Builder()
                .addSharedElements(mapOf(sharedView to sharedView.transitionName))
                .build()
        )
    }catch (t: Throwable){
        println(t.message)
    }
}