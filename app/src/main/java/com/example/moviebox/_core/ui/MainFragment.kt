package com.example.moviebox._core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moviebox.R
import com.example.moviebox.databinding.MainFragmentBinding
import com.example.moviebox.home.ui.HomeFragment
import com.example.moviebox.maps.ui.MapsFragment
import com.example.moviebox.profile.ui.ProfileFragment
import com.example.moviebox.settings.ui.SettingsFragment

class MainFragment : Fragment(), BackPressedMonitor {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            navigateToFragment(HomeFragment.newInstance())
        }
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        // обработка нажатия кнопок BottomNavigationView
        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> returnToHome()
                R.id.action_profile -> navigateToFragment(ProfileFragment.newInstance())
                R.id.action_map ->  navigateToFragment(MapsFragment.newInstance())
                R.id.action_settings -> navigateToFragment(SettingsFragment.newInstance())
            }
            true
        }
    }

    private fun returnToHome() {
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun navigateToFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.host_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // обработка события по нажатию кнопки "Назад" во фрагменте
    override fun onBackPressed(): Boolean {
        // Если backStack не пустой:
        if (childFragmentManager.backStackEntryCount > 0) {
            // опустошаем его и переходим на последний в бэкстеке фрагмент (MainFragment)
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // перемещаем фокус на кнопку Home в BottomNavigationView
            binding.bottomNavView.selectedItemId = R.id.action_home
            return true
        }
        return false
    }
}