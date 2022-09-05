package com.example.moviebox.settings.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.moviebox.BuildConfig
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        // инициализируем переключатель в соотвоествии с сохраненными настройками
        switcherInit()

        // обработка нажатия на переключатель отображения взрослого контента
        switchAdult.setOnClickListener {
            if (switchAdult.isChecked) {
                // запускаем диалог с подтверждением возраста
                showConfirmingDialog()
            } else {
                // сохраняем положение переключателся в настройках приложения
                saveAdultSettings()
            }
        }
        // обработка нажатия на кнопку "О приложении"
        aboutButton.setOnClickListener {
            showAboutDialog()
        }
    }

    // метод инициализации переключателя в соотвоествии с сохраненными настройками
    private fun switcherInit() = with(binding) {
        var withAdult = false
        // загружаем настройки приложения из SharedPreferences
        activity?.let {
            withAdult = it.getPreferences(Context.MODE_PRIVATE).getBoolean(INCLUDE_ADULT_KEY, false)
        }
        switchAdult.isChecked = withAdult
    }

    // метод сохранения положения переключателя взрослого контента в настройках приложения
    private fun saveAdultSettings() = with(binding) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        sharedPref?.let {
            it
                .edit()
                .putBoolean(INCLUDE_ADULT_KEY, switchAdult.isChecked)
                .apply()
        }
    }

    // метод запуска диалога с подтверждением включения взрослого контента
    private fun showConfirmingDialog() {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_adultsonly)
            .setTitle(R.string.attention_adult_content)
            .setMessage(R.string.adult_age_confirm)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                saveAdultSettings()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                binding.switchAdult.isChecked = false
            }
            .create()
            .show()
    }

    // метод запуска диалога с подтверждением включения взрослого контента
    private fun showAboutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.about_app)
            .setMessage(aboutAppMessageBuild())
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    // метод сборки сообщения для диалогового окна "О приложении"
    private fun aboutAppMessageBuild(): String {
        val result = StringBuilder()
        result.append(getString(R.string.app_id) + BuildConfig.APPLICATION_ID + "\n")
        result.append(getString(R.string.app_version) + BuildConfig.VERSION_NAME + "\n")
        result.append(getString(R.string.app_build_type) + BuildConfig.BUILD_TYPE + "\n")
        return result.toString()
    }

    companion object {
        // ключ для чтения/записи положения переключателя взрослого контента в SharedPreferences
        private const val INCLUDE_ADULT_KEY = "ADULT_KEY"

        fun newInstance() = SettingsFragment()
    }
}