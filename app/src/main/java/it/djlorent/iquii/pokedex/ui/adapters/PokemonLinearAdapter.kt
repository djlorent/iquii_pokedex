package it.djlorent.iquii.pokedex.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.djlorent.iquii.pokedex.ui.models.PokemonState
import it.djlorent.iquii.pokedex.ui.views.PokemonLinearView

class PokemonLinearAdapter(
    private val itemClickListener: (Any) -> Unit,
    private val itemLongClickListener: (Any) -> Unit,
    private val imageLoadCompleteListener: (Any) -> Unit,
): ListAdapter<PokemonState, PokemonLinearAdapter.ViewHolder>(DiffCallback) {

    init {
        this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PokemonLinearView(parent.context).apply {
                itemClickListener = this@PokemonLinearAdapter.itemClickListener
                itemLongClickListener = this@PokemonLinearAdapter.itemLongClickListener
                imageLoadCompleteListener = this@PokemonLinearAdapter.imageLoadCompleteListener
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            (holder.itemView as PokemonLinearView).bind(it)
        }
    }

    fun appendItems(newList: List<PokemonState>){
        val mergedList = currentList.plus(newList)
        submitList(mergedList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object DiffCallback : DiffUtil.ItemCallback<PokemonState>() {

        override fun areItemsTheSame(oldItem: PokemonState, newItem: PokemonState): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PokemonState, newItem: PokemonState): Boolean {
            return oldItem.pokemon.id == newItem.pokemon.id
        }
    }
}