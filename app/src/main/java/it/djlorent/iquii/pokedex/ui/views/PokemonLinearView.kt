package it.djlorent.iquii.pokedex.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.Observable
import it.djlorent.iquii.pokedex.R
import it.djlorent.iquii.pokedex.databinding.ViewLinearPokemonBinding
import it.djlorent.iquii.pokedex.extensions.onClick
import it.djlorent.iquii.pokedex.extensions.onLongClick
import it.djlorent.iquii.pokedex.ui.models.PokemonState

open class PokemonLinearView : PokemonView<ViewLinearPokemonBinding, PokemonState> {
    var pokeballClickListener: ((PokemonState) -> Unit)? = null
    private val pokeballView: ImageView

    init {
        binding = ViewLinearPokemonBinding.inflate(LayoutInflater.from(context),this,true)
        imagePokemonView = binding.pokemonImageView
        pokeballView = binding.pokeballView

        with(binding.root) {
            onClick { itemClickListener?.invoke(binding.pokemonImageView, binding.pokemonModel!!) }
        }

        with(pokeballView){
            onClick { pokeballClickListener?.invoke(binding.pokemonModel!!) }
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun bind(model: PokemonState) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        clear()
        setModel(model)
        loadImage(model.pokemon.image)
    }

    fun setModel(model: PokemonState){
        binding.pokemonModel = model
        setFavoriteIcon(model)
        binding.pokemonModel?.addOnPropertyChangedCallback(propertyChangedCallback)
    }

    override fun clear() {
        binding.pokemonModel?.removeOnPropertyChangedCallback(propertyChangedCallback)
        binding.pokemonModel = null
        super.clear()
    }

    override fun onImageLoadSuccess() = imageLoadCompleteListener?.invoke(binding.pokemonModel!!)
    override fun onImageLoadFail() = imageLoadFailListener?.invoke(binding.pokemonModel!!)

    private val propertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            setFavoriteIcon(observable as PokemonState)
        }
    }

    private fun setFavoriteIcon(model: PokemonState){
        pokeballView.setImageResource(
            if(model.isFavorite) R.drawable.ic_pokeball_red else R.drawable.ic_pokeball
        )
    }
}