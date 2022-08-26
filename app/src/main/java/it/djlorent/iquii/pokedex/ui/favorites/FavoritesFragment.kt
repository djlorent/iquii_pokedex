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
import dagger.hilt.android.AndroidEntryPoint
import it.djlorent.iquii.pokedex.R
import it.djlorent.iquii.pokedex.databinding.FragmentFavoritesBinding
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.adapters.Pagination
import it.djlorent.iquii.pokedex.ui.adapters.PokemonGridAdapter
import it.djlorent.iquii.pokedex.ui.adapters.PokemonLinearAdapter
import it.djlorent.iquii.pokedex.ui.models.FavoriteManagerResult
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var pokemonAdapter: PokemonGridAdapter
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        initRecyclerView()

        addSubscriptions()

        return binding.root
    }

    private fun initRecyclerView(){
        pokemonAdapter = PokemonGridAdapter(
            itemClickListener = { model ->
                (model as Pokemon).let {
                    Toast.makeText(requireContext(), "${it.name} clicked!", Toast.LENGTH_SHORT).show()
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
            imageLoadCompleteListener = { }
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