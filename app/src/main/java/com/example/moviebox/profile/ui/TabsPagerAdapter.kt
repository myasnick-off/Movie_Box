package com.example.moviebox.profile.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var tabAmount: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = tabAmount

    // запуск соответствующего фрагмента для каждой страницы TabLayout
    override fun createFragment(position: Int): Fragment {
        return TabFragment.newInstance(position)
    }
}