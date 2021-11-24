package com.example.moviebox.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviebox.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        switcherInit()

        // обработка нажатия на переключатель отображения взрослого контента
        switchAdult.setOnClickListener {
            // записываем положение переключателя в SharedPreferences
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref?.let { it
                .edit()
                .putBoolean(INCLUDE_ADULT_KEY, switchAdult.isChecked)
                .apply()
            }
//            Snackbar.make(profile, if(switchAdult.isChecked) "ON" else "OFF", Snackbar.LENGTH_SHORT).show()
        }

    }

    // метод инициализации переключателей настройки
    private fun switcherInit() = with(binding) {
        var withAdult = false
        // загружаем настройки приложения из SharedPreferences
        activity?.let {
            withAdult = it.getPreferences(Context.MODE_PRIVATE).getBoolean(INCLUDE_ADULT_KEY, false)
        }
        switchAdult.isChecked = withAdult
    }

    companion object {
        // ключ для чтения/записи положения переключателя взрослого контента в SharedPreferences
        private const val INCLUDE_ADULT_KEY = "ADULT_KEY"

        fun newInstance() = SettingsFragment()
    }
}