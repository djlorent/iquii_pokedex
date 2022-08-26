package it.djlorent.iquii.pokedex.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import it.djlorent.iquii.pokedex.R
import it.djlorent.iquii.pokedex.databinding.ViewGridPokemonBinding
import it.djlorent.iquii.pokedex.extensions.dimenToPx
import it.djlorent.iquii.pokedex.extensions.onClick
import it.djlorent.iquii.pokedex.extensions.onLongClick
import it.djlorent.iquii.pokedex.extensions.screenWidth
import it.djlorent.iquii.pokedex.models.Pokemon

class PokemonGridView : PokemonView<ViewGridPokemonBinding, Pokemon> {

    private val gridPadding by lazy { context.dimenToPx(R.dimen.gridPadding) }

    private val width by lazy { (screenWidth().toFloat() - (2.0 * gridPadding)) / 2 }
    private val height by lazy { width }

    init {
        binding = ViewGridPokemonBinding.inflate(LayoutInflater.from(context), this, true)
        imagePokemonView = binding.pokemonImageView

        with(binding.root) {
            onClick { itemClickListener?.invoke(binding.pokemonModel!!) }
            onLongClick { itemLongClickListener?.invoke(binding.pokemonModel!!) }
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun bind(model: Pokemon) {
        layoutParams = LayoutParams(width.toInt(), height.toInt())
        clear()
        setModel(model)
        loadImage(model.image)
    }

    fun setModel(model: Pokemon){
        binding.pokemonModel = model
    }

    override fun clear() {
        binding.pokemonModel = null
        super.clear()
    }

    override fun onImageLoadSuccess() = imageLoadCompleteListener?.invoke(binding.pokemonModel!!)
}