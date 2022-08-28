package it.djlorent.iquii.pokedex.ui.pokemonDetails

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import it.djlorent.iquii.pokedex.R
import it.djlorent.iquii.pokedex.databinding.FragmentPokemonDetailsBinding
import it.djlorent.iquii.pokedex.extensions.withSuccessListener
import it.djlorent.iquii.pokedex.models.PokemonType
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postponeEnterTransition()

        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)

        pokemonDetailsViewModel.pokemonDetails.observe(viewLifecycleOwner) {
            it?.let{
                binding.pokemonModel = it
                loadTypes(it.types!!)
                loadImage(it.image)
            }
        }

        return binding.root
    }

    private fun loadImage(url: String){
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .withSuccessListener { startPostponedEnterTransition() }
            .into(binding.image)
    }

    private fun loadTypes(types: List<PokemonType>){
        types.forEach { type ->
            val chip = Chip(requireContext()).apply {
                text = type.name
                width = WRAP_CONTENT
                height = WRAP_CONTENT
                setTextColor(resources.getColor(R.color.white, null))
                setChipBackgroundColorResource(getTypeColor(type))
            }

            binding.types.addView(chip)
        }
    }

    private fun getTypeColor(type: PokemonType): Int {
        return when(type){
            PokemonType.normal -> R.color.normal
            PokemonType.fighting -> R.color.fighting
            PokemonType.flying -> R.color.flying
            PokemonType.poison -> R.color.poison
            PokemonType.ground -> R.color.ground
            PokemonType.rock -> R.color.rock
            PokemonType.bug -> R.color.bug
            PokemonType.ghost -> R.color.ghost
            PokemonType.steel -> R.color.steel
            PokemonType.fire -> R.color.fire
            PokemonType.water -> R.color.water
            PokemonType.grass -> R.color.grass
            PokemonType.electric -> R.color.electric
            PokemonType.psychic -> R.color.psychic
            PokemonType.ice -> R.color.ice
            PokemonType.dragon -> R.color.dragon
            PokemonType.dark -> R.color.dark
            PokemonType.fairy -> R.color.fairy
            else -> R.color.black
        }
    }
}