package it.djlorent.iquii.pokedex.delegates

import androidx.lifecycle.*
import it.djlorent.iquii.pokedex.BottomNavigationActivity
import kotlinx.coroutines.launch

interface BarVisibilityDelegate {
    fun registerBarVisibility(owner: LifecycleOwner, set: Set<Int>)
}

class BarVisibilityDelegateImpl : BarVisibilityDelegate, DefaultLifecycleObserver {
    private lateinit var setIds: Set<Int>

    override fun registerBarVisibility(owner: LifecycleOwner, set: Set<Int>) {
        owner.lifecycle.addObserver(this)
        setIds = set
    }

    override fun onCreate(owner: LifecycleOwner) {
        val activity = owner as BottomNavigationActivity

        owner.lifecycleScope.launch{
            owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                activity.navController.currentBackStackEntryFlow.collect {
                      activity.setBottomNavigationVisibility(it.destination.id in setIds)
                }
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}