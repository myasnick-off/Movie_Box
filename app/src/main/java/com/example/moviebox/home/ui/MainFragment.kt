package com.example.moviebox.home.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox._core.ui.model.AppState
import com.example.moviebox.R
import com.example.moviebox.databinding.MainFragmentBinding
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import com.example.moviebox._core.ui.model.Category
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox.details.ui.DetailsFragment
import com.example.moviebox._core.ui.OnItemViewClickListener
import com.example.moviebox.filter.ui.FilterFragment
import com.example.moviebox.search.ui.SearchFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация основного вертикального RecyclerView
        mainRecycler
            .layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = MainFragmentAdapter(onMovieItemClickListener)
        mainRecycler.adapter = adapter

        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

        if (savedInstanceState == null) {
            requireMovieList()
        } else {
            getMovieList(savedInstanceState)
        }

        // обработка события по нажатию кнопок меню
        mainToolbar.inflateMenu(R.menu.menu_main)
        mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_filter -> {
                    val manager = activity?.supportFragmentManager
                    manager?.let {
                        it.beginTransaction()
                            .add(R.id.container, FilterFragment.newInstance(hasAdult))
                            .addToBackStack("SearchFragment")
                            .commit()
                    }
                    true
                }
                else -> true
            }
        }
        // инициализация меню поиска
        val searchItem = mainToolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search film..."
        // обработка событий меню поиска
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(phrase: String?): Boolean {
                phrase?.let {
                    val searchBundle = Bundle().apply {
                        putString(ARG_SEARCH_PHRASE, phrase)
                        putBoolean(ARG_WITH_ADULT, hasAdult)
                    }
                    val manager = activity?.supportFragmentManager
                    manager?.let {
                        it.beginTransaction()
                            .add(R.id.container, SearchFragment.newInstance(searchBundle))
                            .addToBackStack("SearchFragment")
                            .commit()
                        return true
                    }
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
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
            AppState.Loading -> mainProgressBar.progressItem.show()
            is AppState.Success -> {
                mainProgressBar.progressItem.hide()
                movieList = appState.categoryData
                adapter.setData(movieList as ArrayList<Category>)
            }
            is AppState.Error -> {
                mainProgressBar.progressItem.hide()
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
        private const val ARG_SEARCH_PHRASE = "SEARCH_PHRASE"
        private const val ARG_WITH_ADULT = "WITH_ADULT"

        fun newInstance() = MainFragment()
    }
}