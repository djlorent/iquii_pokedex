package it.djlorent.iquii.pokedex.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import it.djlorent.iquii.pokedex.ui.views.PokemonLinearView

class PokemonLinearAdapter(
    private val itemClickListener: ((View, Any) -> Unit)? = null,
    private val itemLongClickListener: ((Any) -> Unit)? = null,
    private val pokeballClickListener: ((Any) -> Unit)? = null,
    private val imageLoadCompleteListener: ((Any) -> Unit)? = null,
    private val imageLoadFailListener: ((Any) -> Unit)? = null,
): ListAdapter<PokemonState, PokemonLinearAdapter.ViewHolder>(DiffCallback) {

    init {
        this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PokemonLinearView(parent.context).apply {
                itemClickListener = this@PokemonLinearAdapter.itemClickListener
                itemLongClickListener = this@PokemonLinearAdapter.itemLongClickListener
                pokeballClickListener = this@PokemonLinearAdapter.pokeballClickListener
                imageLoadCompleteListener = this@PokemonLinearAdapter.imageLoadCompleteListener
                imageLoadFailListener = this@PokemonLinearAdapter.imageLoadFailListener
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            (holder.itemView as PokemonLinearView).bind(it)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object DiffCallback : DiffUtil.ItemCallback<PokemonState>() {

        override fun areItemsTheSame(oldItem: PokemonState, newItem: PokemonState): Boolean {
            return oldItem.pokemon.id == newItem.pokemon.id
        }

        override fun areContentsTheSame(oldItem: PokemonState, newItem: PokemonState): Boolean {
            return oldItem.pokemon.id == newItem.pokemon.id
                    && oldItem.pokemon.name == newItem.pokemon.name
                    && oldItem.isFavorite == oldItem.isFavorite
        }
    }
}