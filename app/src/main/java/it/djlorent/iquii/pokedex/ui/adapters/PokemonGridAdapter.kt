package it.djlorent.iquii.pokedex.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.views.PokemonGridView


class PokemonGridAdapter(
    private val itemClickListener: ((View, Any) -> Unit)? = null,
    private val itemLongClickListener: ((Any) -> Unit)? = null,
    private val pokeballClickListener: ((Any) -> Unit)? = null,
    private val imageLoadCompleteListener: ((Any) -> Unit)? = null,
    private val imageLoadFailListener: ((Any) -> Unit)? = null,
): ListAdapter<Pokemon, PokemonGridAdapter.ViewHolder>(DiffCallback) {

    init {
        this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PokemonGridView(parent.context).apply {
                itemClickListener = this@PokemonGridAdapter.itemClickListener
                itemLongClickListener = this@PokemonGridAdapter.itemLongClickListener
                pokeballClickListener = this@PokemonGridAdapter.pokeballClickListener
                imageLoadCompleteListener = this@PokemonGridAdapter.imageLoadCompleteListener
                imageLoadFailListener = this@PokemonGridAdapter.imageLoadFailListener
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            (holder.itemView as PokemonGridView).bind(it)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }
}
