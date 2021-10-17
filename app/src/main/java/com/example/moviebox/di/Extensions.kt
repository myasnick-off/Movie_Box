package com.example.moviebox.di

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    actionText: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit
) {
    Snackbar
        .make(this, message, length)
        .setAction(actionText, action)
        .show()
}

fun View.show(): View {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE)
        visibility = View.GONE
    return this
}