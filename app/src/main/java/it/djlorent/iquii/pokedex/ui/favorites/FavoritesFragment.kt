package it.djlorent.iquii.pokedex.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.djlorent.iquii.pokedex.R
import it.djlorent.iquii.pokedex.databinding.FragmentFavoritesBinding
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.adapters.PokemonGridAdapter
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var pokemonAdapter: PokemonGridAdapter
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var selectedPokemonId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (selectedPokemonId != null) {
            postponeEnterTransition()
        }

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        initRecyclerView()

        addSubscriptions()

        return binding.root
    }

    private fun initRecyclerView(){
        pokemonAdapter = PokemonGridAdapter(
            itemClickListener = { view, model ->
                (model as Pokemon).let {
                    selectedPokemonId = it.id

                    try {
                        findNavController().navigate(
                            FavoritesFragmentDirections.actionNavigationFavoritesToPokemonDetailsFragment(it.id),
                            FragmentNavigator.Extras.Builder()
                                .addSharedElements(
                                    mapOf(view to view.transitionName)
                                ).build()
                        )
                    }catch (t: Throwable){
                        println(t.message)
                    }
                }
            },
            itemLongClickListener = { model ->
                (model as Pokemon).let{
                    lifecycleScope.launch {
                        val result = favoritesViewModel.removeFromFavorite(it)

                        val printText = when(result){
                            FavoriteManagerResult.Removed -> {
                                requireContext().resources.getString(R.string.removedToFavorite, it.name)
                            }
                            else -> requireContext().resources.getString(R.string.error)
                        }

                        Toast.makeText(requireContext(), printText, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            imageLoadCompleteListener = {model ->
                (model as Pokemon).let {
                    if (it.id == selectedPokemonId) {
                        startPostponedEnterTransition()
                    }
                }
            }
        )
        binding.favoritesView.adapter = pokemonAdapter
   }

    private fun addSubscriptions(){
        subscribeOnStarted {
            favoritesViewModel.favoritesUiStateFlow.collect {
                binding.loadingSpinner.isVisible = it.isLoading && pokemonAdapter.itemCount == 0
            }
        }

        subscribeOnStarted {
            favoritesViewModel.favoritesFlow.collect {
                pokemonAdapter.submitList(it)
            }
        }
    }

    private fun subscribeOnStarted(subscriber : suspend () -> Unit){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                subscriber()
            }
        }
    }
}