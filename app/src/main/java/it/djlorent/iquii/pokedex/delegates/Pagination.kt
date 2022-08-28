package it.djlorent.iquii.pokedex.delegates

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.djlorent.iquii.pokedex.Constants

class Pagination(
    private val isLoading: () -> Boolean,
    private val loadMore: () -> Unit,
    private val onFinish: () -> Boolean
) : RecyclerView.OnScrollListener() {

    private val threshold = Constants.PAGINATION_THRESHOLD

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