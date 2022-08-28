package it.djlorent.iquii.pokedex.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import it.djlorent.iquii.pokedex.extensions.withFailListener
import it.djlorent.iquii.pokedex.extensions.withSuccessListener
import it.djlorent.iquii.pokedex.ui.models.PokemonState

abstract class PokemonView<T: ViewDataBinding, M> : FrameLayout {
    lateinit var binding: T
    lateinit var imagePokemonView: ImageView

    var itemClickListener: ((View, M) -> Unit)? = null
    var itemLongClickListener: ((M) -> Unit)? = null
    var imageLoadCompleteListener: ((M) -> Unit)? = null
    var imageLoadFailListener: ((M) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    abstract fun bind(model: M)

    @CallSuper
    open fun clear() {
        loadImage(null)
    }

    open fun loadImage(url: String?) {
        val glide = Glide.with(this)

        if(url != null)
            glide.load(url)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .withSuccessListener { onImageLoadSuccess() }
                .withFailListener { onImageLoadFail()  }
                .into(imagePokemonView)
        else {
            glide
                .clear(imagePokemonView)
        }
    }

    abstract fun onImageLoadSuccess(): Unit?
    abstract fun onImageLoadFail(): Unit?
}