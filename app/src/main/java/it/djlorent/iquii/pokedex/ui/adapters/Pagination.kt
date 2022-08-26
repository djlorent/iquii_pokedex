package it.djlorent.iquii.pokedex.ui.adapters

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Pagination(
    private val isLoading: () -> Boolean,
    private val loadMore: () -> Unit,
    private val onFinish: () -> Boolean
) : RecyclerView.OnScrollListener() {

    private val threshold = 20

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let { layMan ->
            val lastPosition = when (layMan) {
                is LinearLayoutManager -> layMan.findLastVisibleItemPosition()
                is GridLayoutManager -> layMan.findLastVisibleItemPosition()
                else -> return
            }

            if (!isLoading() && !onFinish()) {
                if (lastPosition >= layMan.itemCount - threshold) {
                    loadMore()
                }
            }
        }
    }
}