package com.example.moviebox.main.ui

// интерфейс для делигирования обработки событий по нажатию копки "назад" в реализующий его франмент
interface BackPressedMonitor {
    fun onBackPressed(): Boolean
}