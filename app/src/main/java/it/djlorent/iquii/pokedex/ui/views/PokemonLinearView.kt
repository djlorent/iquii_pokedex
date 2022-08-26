package it.djlorent.iquii.pokedex.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import it.djlorent.iquii.pokedex.databinding.ViewLinearPokemonBinding
import it.djlorent.iquii.pokedex.extensions.*
import it.djlorent.iquii.pokedex.models.Pokemon

class PokemonLinearView : FrameLayout {

    private var binding: ViewLinearPokemonBinding
    private var imageView: ImageView

    init {
        binding = ViewLinearPokemonBinding.inflate(LayoutInflater.from(context),this,true)
        imageView = binding.pokemonImageView

        with(binding.root) {
            onClick { itemClickListener?.invoke(binding.pokemonModel!!) }
            onLongClick { itemLongClickListener?.invoke(binding.pokemonModel!!) }
        }
    }

    var itemClickListener: ((Pokemon) -> Unit)? = null
    var itemLongClickListener: ((Pokemon) -> Unit)? = null
    var imageLoadCompleteListener: ((Pokemon) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun bind(pokemon: Pokemon) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        clear()
        setModel(pokemon)
        loadImage(pokemon.image)
    }

    private fun setModel(model: Pokemon){
        binding.pokemonModel = model
    }

    private fun clear() {
        binding.pokemonModel = null
        loadImage(null)
    }

    private fun loadImage(url: String?) {
        val glide = Glide.with(this)

        if(url != null)
            glide.load(url)
                .transition(withCrossFade(200))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .withSuccessListener { onImageLoadSuccess() }
                .into(imageView)
        else {
            glide
                .clear(imageView)
        }
    }

    private fun onImageLoadSuccess() = imageLoadCompleteListener?.invoke(binding.pokemonModel!!)
}
