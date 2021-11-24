package com.example.moviebox.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.AppState
import com.example.moviebox.R
import com.example.moviebox.databinding.MainFragmentBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.di.showSnackBar
import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.rest_entities.MovieDTO
import com.example.moviebox.ui.details.DetailsFragment
import com.example.moviebox.ui.OnItemViewClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieList: List<Category>
    private lateinit var adapter: MainFragmentAdapter
    private var hasAdult: Boolean = false

    // реализация события по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {
        override fun onItemClicked(movieId: Long) {
            val manager = activity?.supportFragmentManager
            // передаем во фрагмент с деталями фильма его ID
            manager?.let {
                val bundle = Bundle().apply {
                    putLong(DetailsFragment.KEY_BUNDLE, movieId)
                }
                manager
                    .beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("detailsFragment")
                    .commit()
            }
        }

        override fun onItemLongClicked(movie: MovieDTO, view: View) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация основного вертикального RecyclerView
        binding.mainRecycler
            .layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = MainFragmentAdapter(onMovieItemClickListener)
        binding.mainRecycler.adapter = adapter

        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

        if (savedInstanceState == null) {
            requireMovieList()
        } else {
            getMovieList(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(MOVIE_LIST_SAVE_KEY, movieList as ArrayList<Category>)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            AppState.Loading -> mainProgressBar.show()
            is AppState.Success -> {
                mainProgressBar.hide()
                movieList = appState.categoryData
                adapter.setData(movieList as ArrayList<Category>)
            }
            is AppState.Error -> {
                mainProgressBar.hide()
                main.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { requireMovieList() }
            }
        }
    }

    // метод запроса списков с фильмами с сервера
    private fun requireMovieList() {
        // загружаем настройки приложения
        if (loadPreferences()) {            // если настройки изменились:
            viewModel.resetDataLoaded()     // сбрасывам флаг о наличии загруженных данных во viewModel
        }
        // отправляем зарпос на сервер
        viewModel.getMovieListFromServer(hasAdult)
    }

    // метод запроса списков с фильмами из bundle
    private fun getMovieList(bundle: Bundle) {
        movieList = bundle.getParcelableArrayList(MOVIE_LIST_SAVE_KEY)!!
        adapter.setData(movieList as ArrayList<Category>)
    }

    // метод загрузки настроек приложения из SharedPreferences
    // возвращает true если настройки изменились
    private fun loadPreferences(): Boolean {
        activity?.let {
            val adultSetting =
                it.getPreferences(Context.MODE_PRIVATE).getBoolean(INCLUDE_ADULT_KEY, false)
            if (adultSetting != hasAdult) {
                hasAdult = adultSetting
                return true
            }
        }
        return false
    }

    companion object {
        private const val MOVIE_LIST_SAVE_KEY = "MOVIE_LIST_SAVE_KEY"
        private const val INCLUDE_ADULT_KEY = "ADULT_KEY"

        fun newInstance() = MainFragment()
    }
}