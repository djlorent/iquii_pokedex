package it.djlorent.iquii.pokedex.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.djlorent.iquii.pokedex.models.Pokemon
import it.djlorent.iquii.pokedex.ui.views.PokemonLinearView

class PokemonRecyclerViewAdapter(
    private val itemClickListener: (Pokemon) -> Unit,
    private val itemLongClickListener: (Pokemon) -> Unit,
    private val imageLoadCompleteListener: (Pokemon) -> Unit,
): ListAdapter<Pokemon, PokemonRecyclerViewAdapter.ViewHolder>(DiffCallback) {

    init {
        this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PokemonLinearView(parent.context).apply {
                itemClickListener = this@PokemonRecyclerViewAdapter.itemClickListener
                itemLongClickListener = this@PokemonRecyclerViewAdapter.itemLongClickListener
                imageLoadCompleteListener = this@PokemonRecyclerViewAdapter.imageLoadCompleteListener
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            (holder.itemView as PokemonLinearView).bind(it)
        }
    }

    fun appendItems(newList: List<Pokemon>){
        val mergedList = currentList.plus(newList)
        submitList(mergedList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
