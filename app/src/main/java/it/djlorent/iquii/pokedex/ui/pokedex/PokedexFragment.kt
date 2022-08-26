package it.djlorent.iquii.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import it.djlorent.iquii.pokedex.databinding.FragmentPokedexBinding
import it.djlorent.iquii.pokedex.ui.adapters.Pagination
import it.djlorent.iquii.pokedex.ui.adapters.PokemonRecyclerViewAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokedexFragment : Fragment() {

    private lateinit var binding: FragmentPokedexBinding
    private lateinit var pokemonAdapter: PokemonRecyclerViewAdapter
    private val pokedexViewModel: PokedexViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)

        initRecyclerView()

        addSubscriptions()

        return binding.root
    }

    private fun initRecyclerView(){
        pokemonAdapter = PokemonRecyclerViewAdapter(
            itemClickListener = { item ->
                Toast.makeText(requireContext(), "${item.name} clicked!", Toast.LENGTH_SHORT).show()
            },
            itemLongClickListener = { item ->
                Toast.makeText(requireContext(), "${item.name} LONG clicked!", Toast.LENGTH_SHORT).show()
            },
            imageLoadCompleteListener = { }
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

    private fun addSubscriptions(){
        subscribeOnStarted {
            pokedexViewModel.pokedexUiStateFlow.collect {
                binding.loadingSpinner.isVisible = it.isLoading && pokemonAdapter.itemCount == 0
                binding.loadingSpinnerAppend.isVisible = it.isLoading && pokemonAdapter.itemCount > 0
            }
        }

        subscribeOnStarted {
            pokedexViewModel.pokeStateFlow.collect {
                pokemonAdapter.appendItems(it)
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

    override fun onStop() {
        super.onStop()
        pokedexViewModel.resetPage()
    }
}