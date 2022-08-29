package it.djlorent.iquii.pokedex.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import it.djlorent.iquii.pokedex.R
import it.djlorent.iquii.pokedex.databinding.FragmentFavoritesBinding
import it.djlorent.iquii.pokedex.extensions.navigateToWithSharedView
import it.djlorent.iquii.pokedex.extensions.subscribeOnStarted
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.adapters.PokemonGridAdapter
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
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
            itemClickListener = ::navigateTo,
            itemLongClickListener = ::removeFavorite,
            pokeballClickListener = ::removeFavorite,
            imageLoadCompleteListener = ::imageLoadListener,
            imageLoadFailListener = ::imageLoadListener,
        )
        binding.favoritesView.adapter = pokemonAdapter
    }

    private fun navigateTo(view: View, model: Any){
        (model as Pokemon).let {
            selectedPokemonId = it.id
            navigateToWithSharedView(
                FavoritesFragmentDirections.actionNavigationFavoritesToPokemonDetailsFragment(
                    it.id,
                    it.name
                ),
                view
            )
        }
    }

    private fun removeFavorite(model: Any) {
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
    }

    private fun imageLoadListener(model: Any){
        (model as Pokemon).let {
            if (it.id == selectedPokemonId) {
                startPostponedEnterTransition()
            }
        }
    }

    private fun addSubscriptions(){
        subscribeOnStarted {
            favoritesViewModel.favoritesUiStateFlow.collect {
                binding.loadingSpinner.isVisible = it.isLoading && pokemonAdapter.itemCount == 0
                binding.noFavoritesText.isVisible = !it.isLoading && pokemonAdapter.itemCount == 0
            }
        }

        subscribeOnStarted {
            favoritesViewModel.favoritesFlow.collect {
                binding.noFavoritesText.isVisible = it.isEmpty()
                pokemonAdapter.submitList(it)
            }
        }
    }
}