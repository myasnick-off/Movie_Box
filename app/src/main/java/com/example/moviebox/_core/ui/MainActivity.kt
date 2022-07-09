package com.example.moviebox._core.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviebox.R
import com.example.moviebox._core.ui.store.MainStore
import com.example.moviebox._core.ui.store.MainStoreHolder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainStoreHolder {

    override val mainStore: MainStore by inject()
    private val viewModel: MainViewModel by viewModel() { parametersOf(mainStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.loadData(withAdult = getAdultSettings())
    }

    // обработка события по нажатию кнопки "Назад"
    override fun onBackPressed() {
        val mainFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (mainFragment is BackPressedMonitor) {
            // проверяем если нажатие кнопки "Назад" в main-фрагменте вернуло false,
            // то отдаем обработку кнопки "Назад" активити
            if (!mainFragment.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun getAdultSettings(): Boolean {
        return getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_KEY, false)
    }

    companion object {
        private const val ADULT_KEY = "adult_key"
    }
}