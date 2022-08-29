package it.djlorent.iquii.pokedex.ui.pokedex

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
import it.djlorent.iquii.pokedex.databinding.FragmentPokedexBinding
import it.djlorent.iquii.pokedex.delegates.Pagination
import it.djlorent.iquii.pokedex.extensions.navigateToWithSharedView
import it.djlorent.iquii.pokedex.extensions.subscribeOnStarted
import it.djlorent.iquii.pokedex.ui.adapters.PokemonLinearAdapter
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokedexFragment : Fragment() {

    private lateinit var binding: FragmentPokedexBinding
    private lateinit var pokemonAdapter: PokemonLinearAdapter
    private val pokedexViewModel: PokedexViewModel by viewModels()
    private var selectedPokemonId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (selectedPokemonId != null) {
            postponeEnterTransition()
        }

        binding = FragmentPokedexBinding.inflate(inflater, container, false)

        initRecyclerView()

        addSubscriptions()

        return binding.root
    }

    private fun initRecyclerView(){
        pokemonAdapter = PokemonLinearAdapter(
            itemClickListener =  ::navigateTo,
            itemLongClickListener = ::manageFavorite,
            pokeballClickListener = ::manageFavorite,
            imageLoadCompleteListener = ::imageLoadListener,
            imageLoadFailListener = ::imageLoadListener
        )
        binding.pokedexView.adapter = pokemonAdapter
        binding.pokedexView.addOnScrollListener(
            Pagination(
                isLoading = { pokedexViewModel.pokedexUiStateFlow.value.isLoading },
                loadMore = { pokedexViewModel.requestNewPage() },
                onFinish = { pokedexViewModel.pokedexUiStateFlow.value.isComplete }
            )
        )
    }

    private fun navigateTo(view: View, model: Any) {
        (model as PokemonState).let {
            selectedPokemonId = it.pokemon.id
            navigateToWithSharedView(
                PokedexFragmentDirections.actionNavigationPokedexToPokemonDetailsFragment(
                    it.pokemon.id,
                    it.pokemon.name
                ),
                view
            )
        }
    }

    private fun manageFavorite(model: Any){
        (model as PokemonState).let {
            lifecycleScope.launch {
                val result = pokedexViewModel.addOrRemoveToFavorite(it)

                val printText = when(result){
                    FavoriteManagerResult.Added -> {
                        it.updateFavorite(true)
                        requireContext().resources.getString(R.string.addedToFavorite, it.pokemon.name)
                    }
                    FavoriteManagerResult.Removed -> {
                        it.updateFavorite(false)
                        requireContext().resources.getString(R.string.removedToFavorite, it.pokemon.name)
                    }
                    else -> requireContext().resources.getString(R.string.error)
                }

                Toast.makeText(requireContext(), printText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun imageLoadListener(model: Any) {
        (model as PokemonState).let {
            if (it.pokemon.id == selectedPokemonId) {
                startPostponedEnterTransition()
            }
        }
    }

    private fun addSubscriptions(){
        subscribeOnStarted {
            pokedexViewModel.pokedexUiStateFlow.collect {
                binding.loadingSpinner.isVisible = it.isLoading && pokemonAdapter.itemCount == 0
                binding.loadingSpinnerAppend.isVisible = it.isLoading && pokemonAdapter.itemCount > 0
                binding.noPokedexText.isVisible = !it.isLoading && pokemonAdapter.itemCount == 0
            }
        }

        subscribeOnStarted {
            pokedexViewModel.favoritesFlow.collectLatest { favorites ->
                pokemonAdapter.currentList.forEach {
                    it.updateFavorite(favorites.contains(it.pokemon.id))
                }
            }
        }

        pokedexViewModel.pokeStateLive.observe(viewLifecycleOwner) {
            binding.noPokedexText.isVisible = it.isEmpty()
            pokemonAdapter.submitList(it)
        }
    }
}