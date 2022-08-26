package it.djlorent.iquii.pokedex.extensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.databinding.BindingAdapter
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

fun View.onClick(action: (View) -> Unit) = setOnClickListener {
    action(it)
}

fun View.onLongClick(action: (View) -> Unit) = setOnLongClickListener {
    action(it)
    true
}

fun Context.dimenToPx(@DimenRes dimenResId: Int) = resources.getDimensionPixelSize(dimenResId)

fun screenWidth() = Resources.getSystem().displayMetrics.widthPixels

fun screenHeight() = Resources.getSystem().displayMetrics.heightPixels


