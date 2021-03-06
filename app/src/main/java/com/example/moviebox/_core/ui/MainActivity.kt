package com.example.moviebox._core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviebox.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
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
}