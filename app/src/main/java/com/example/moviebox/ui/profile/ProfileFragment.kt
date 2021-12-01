package com.example.moviebox.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    // создаем/вызываем три экземпляра TabViewModel, для каждой страницы TabLayout, с соответствующим ключем
    // эти экземпляры TabViewModel будут размещаться в ViewModelProvider Активити,
    // так что они будут общими между этим фрагментом и соответсвующей страницей TabLayout
    private val historyViewModel: TabViewModel by lazy {
        ViewModelProvider(requireActivity())["KEY_#0", TabViewModel::class.java]
    }
    private val favoriteViewModel: TabViewModel by lazy {
        ViewModelProvider(requireActivity())["KEY_#1", TabViewModel::class.java]
    }
    private val wishlistViewModel: TabViewModel by lazy {
        ViewModelProvider(requireActivity())["KEY_#2", TabViewModel::class.java]
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TabsPagerAdapter
    private var tabSelected = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        // количесто страниц в TabLayout
        val tabsAmount = 3
        // показывать все вкладки страниц TabLayout на экране
        profileTab.tabMode = TabLayout.MODE_FIXED
        // создание адаптера для ViewPager
        adapter = TabsPagerAdapter(childFragmentManager, lifecycle, tabsAmount)
        // присваиваем созданный адаптер нашему ViewPager'у
        profileViewPager.adapter = adapter
        // разрешаем листать страницы ViewPager'а
        profileViewPager.isUserInputEnabled = true

        // связываем TabLayout и ViewPager вместе и выставляем названия страниц
        TabLayoutMediator(profileTab, profileViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.history)
                }
                1 -> {
                    tab.text = getString(R.string.favorite)
                }
                2 -> {
                    tab.text = getString(R.string.wish_list)
                }
            }
        }.attach()

        // обработка событий выбора страниц в TabLayout
        profileTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // сохраняем в переменную номер выбранной страницы
                tab?.let { tabSelected = tab.position }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // обработка события по нажатию кнопок меню
        profileToolBar.inflateMenu(R.menu.menu_profile)
        profileToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_clear_all -> {
                    showDeleteDialog(tabSelected)
                    true
                }
                else -> false
            }
        }

        childFragmentManager.setFragmentResultListener(
            KEY_DELETE_DIALOG,
            viewLifecycleOwner,
            { _, result ->
                val isOkPressed = result.getBoolean(ARG_OK)
                if (isOkPressed) {
                    when (tabSelected) {
                        0 -> historyViewModel.clearHistory()
                        1 -> favoriteViewModel.clearFavorite()
                        2 -> wishlistViewModel.clearWishlist()
                    }
                }
            })
    }

    private fun showDeleteDialog(tabNumber: Int) {
        DeleteDialogFragment(tabNumber).show(childFragmentManager, "DeleteDialog")
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}