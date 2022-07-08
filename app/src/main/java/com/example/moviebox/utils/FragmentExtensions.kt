package com.example.moviebox.utils

import androidx.fragment.app.Fragment
import com.example.moviebox.R

fun Fragment.navigateToFragment(fragment: Fragment) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .add(R.id.container, fragment)
        .addToBackStack(null)
        .commit()
}