package com.example.moviebox.ui.filter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.moviebox.R
import com.example.moviebox.model.rest_entities.GenreDTO

const val ARG_CHECKED_GENRES = "ARG_OK"
const val KEY_GENRES_DIALOG = "KEY_DELETE_DIALOG"

// Диалоговый фрагмент для выбора жанров
class GenresDialogFragment(private val genreList: List<GenreDTO>) : DialogFragment() {

    private var genres = Array(genreList.size) { "" }                   // текстовый массив названий жанров
    private var checkedGenres = BooleanArray(genreList.size)            // массив с флагами выбранных жанров
    private var checkedGenreList: ArrayList<GenreDTO> = arrayListOf()   // список выбранных жанров

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // заполняем массив названиями жанров
        for (i in genreList.indices) {
            genres[i] = genreList[i].name
        }

        // обработчик нажатия на кнопку диалога
        val dialogListener = DialogInterface.OnClickListener { _, _ ->
            // заполняем список выбранными жанрами
            for (i in checkedGenres.indices) {
                if (checkedGenres[i]) {
                    checkedGenreList.add(genreList[i])
                }
            }
            val result = Bundle()
            result.putParcelableArrayList(ARG_CHECKED_GENRES, checkedGenreList)
            parentFragmentManager.setFragmentResult(KEY_GENRES_DIALOG, result)
        }

        // обработчик нажатия на чекбоксы списка
        val multiChoiceListener =
            DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
                // в массиве флагов устанавливаем/сбрасываем флаг выбранного жанра
                checkedGenres[which] = isChecked
            }

        // конфигурируем диалог
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.genres)                                          // заголовок
            .setMultiChoiceItems(genres, checkedGenres, multiChoiceListener)    // список множественного выбора
            .setPositiveButton(R.string.apply, dialogListener)                  // кнопка принятия
            .setNeutralButton(R.string.cancel, null)                     // кнопка отмены
            .create()
    }
}