package com.example.moviebox.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.model.GenreDTO
import com.example.moviebox.databinding.FragmentFilterBinding
import com.example.moviebox.filter.ui.model.FilterSet
import com.example.moviebox.filter.ui.model.GenresAppState
import com.example.moviebox.search.ui.SearchFragment
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.navigateToFragment
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : Fragment() {

    private val viewModel: GenresViewModel by viewModel()
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private var withAdult = false                   // флаг включения взрослого контента
    private var filterSet = FilterSet()             // набор установок фильтра
    private lateinit var genres: List<GenreDTO>     // список жанров

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // при создании вытаскиваем содержимое аргументов (флаг включения взрослого контента)
        arguments?.let {
            withAdult = it.getBoolean(ARG_WITH_ADULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        // создаем наблюдателя и подписываемся на LiveData
        val observer = Observer<GenresAppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        // запрашиваем список жанров с сервера
        viewModel.getGenreListFromServer()

        // инициализируем набор установок фильтра первоначальными параметрами
        filterSet.yearFrom = yearSlider.valueFrom.toInt()
        filterSet.yearTo = yearSlider.valueTo.toInt()
        filterSet.ratingFrom = ratingSlider.valueFrom
        filterSet.ratingTo = ratingSlider.valueTo

        // обработчик изменения положений слайдера выбора года
        yearSlider.addOnChangeListener { slider, _, _ ->
            // заполняем набор установок фильтра новыми значениями
            filterSet.yearFrom = slider.values[0].toInt()
            filterSet.yearTo = slider.values[1].toInt()
        }

        // обработчик изменения положений слайдера выбора рейтинга
        ratingSlider.addOnChangeListener { slider, _, _ ->
            // заполняем набор установок фильтра новыми значениями
            filterSet.ratingFrom = slider.values[0]
            filterSet.ratingTo = slider.values[1]
        }

        // отбработчик нажатия на кнопку выбора жанров
        genresButton.setOnClickListener {
            GenresDialogFragment(genres).show(childFragmentManager, null)
        }

        // обработчик нажатия на кнопку "Применить"
        applyButton.setOnClickListener {
            navigateToFragment(SearchFragment.newInstance(filterSet = filterSet))
        }

        // слушатель ответа от диалогового окна выбора жанров
        childFragmentManager.setFragmentResultListener(
            KEY_GENRES_DIALOG,
            viewLifecycleOwner
        ) { _, result ->
            val checkedGenreList = result.getParcelableArrayList<GenreDTO>(ARG_CHECKED_GENRES)
            checkedGenreList?.let {
                genresButton.text = genreListToString(it)
                filterSet.genres = getGenresId(it)
            }
        }
    }

    private fun renderData(appState: GenresAppState) = with(binding) {
        when (appState) {
            GenresAppState.Loading -> {
                filterProgressBar.root.show()
                filterGroup.hide()
            }
            is GenresAppState.Success -> {
                filterProgressBar.root.hide()
                filterGroup.show()
                genres = appState.genreData
            }
            is GenresAppState.Error -> {
                filterProgressBar.root.hide()
                filterMain.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { viewModel.getGenreListFromServer() }
            }
        }
    }

    // метод преобразования списка жанров в текстовый формат
    private fun genreListToString(genres: List<GenreDTO>): String {
        val result = StringBuilder()
        for (genre in genres) {
            result.append(genre.name)
            if (genre != genres.last()) {
                result.append(", ")
            }
        }
        return result.toString()
    }

    // метод получения списка id жанров из списка жанров
    private fun getGenresId(genres: ArrayList<GenreDTO>): ArrayList<Int> {
        val result: ArrayList<Int> = arrayListOf()
        for (genre in genres) {
            result.add(genre.id)
        }
        return result
    }

    companion object {
        private const val ARG_WITH_ADULT = "with_adult"

        fun newInstance() = FilterFragment()
    }
}