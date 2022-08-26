package it.djlorent.iquii.pokedex.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("capitalizeText")
fun capitalizeText(view: TextView, text: String?) {
    text?.let {
        view.text = it.replaceFirstChar { char ->
            if (char.isLowerCase())
                char.titlecase(Locale.getDefault())
            else char.toString()
        }
    }
}