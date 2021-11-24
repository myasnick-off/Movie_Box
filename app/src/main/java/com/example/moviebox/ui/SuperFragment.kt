package com.example.moviebox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moviebox.R
import com.example.moviebox.databinding.SuperFragmentBinding
import com.example.moviebox.ui.main.MainFragment
import com.example.moviebox.ui.profile.ProfileFragment
import com.example.moviebox.ui.settings.SettingsFragment

class SuperFragment : Fragment(), BackPressedMonitor {

    private var _binding: SuperFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SuperFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.super_container, MainFragment.newInstance())
                .commit()
        }

        // обработка нажатия кнопок BottomNavigationView
        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                R.id.action_profile -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.super_container, ProfileFragment.newInstance())
                        .addToBackStack("ProfileFragment")
                        .commit()
                }
                R.id.action_settings -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.super_container, SettingsFragment.newInstance())
                        .addToBackStack("SettingsFragment")
                        .commit()
                }
            }
            true
        }
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