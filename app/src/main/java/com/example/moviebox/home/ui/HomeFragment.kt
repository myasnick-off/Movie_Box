package com.example.moviebox.home.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.OnItemViewClickListener
import com.example.moviebox._core.ui.model.AppState
import com.example.moviebox.databinding.HomeFragmentBinding
import com.example.moviebox.details.ui.DetailsFragment
import com.example.moviebox.filter.ui.FilterFragment
import com.example.moviebox.home.domain.model.Category
import com.example.moviebox.search.ui.SearchFragment
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.navigateToFragment
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieList: List<Category>
    private lateinit var adapter: HomeFragmentAdapter
    private var hasAdult: Boolean = false

    // реализация события по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {
        override fun onItemClicked(movieId: Long) {
            navigateToFragment(fragment = DetailsFragment.newInstance(movieId))
        }
        override fun onItemLongClicked(movie: MovieDTO, view: View) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("###", "HomeFragment created")
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d("###", "HomeFragment on Create View")
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("###", "HomeFragment on View Created")
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        loadMovieList()
    }

    override fun onStart() {
        super.onStart()
        Log.d("###", "HomeFragment started")
    }

    override fun onResume() {
        super.onResume()
        Log.d("###", "HomeFragment resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d("###", "HomeFragment paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("###", "HomeFragment stopped")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("###", "HomeFragment onDestroyView")
    }

    private fun initView() = with(binding) {
        // инициализация основного вертикального RecyclerView
        mainRecycler
            .layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = HomeFragmentAdapter(onMovieItemClickListener)
        mainRecycler.adapter = adapter

        // обработка события по нажатию кнопок меню
        mainToolbar.inflateMenu(R.menu.menu_main)
        mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_filter -> {
                    navigateToFragment(fragment = FilterFragment.newInstance(hasAdult))
                    true
                }
                else -> false
            }
        }
        // инициализация меню поиска
        val searchItem = mainToolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_film)
        // обработка событий меню поиска
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(phrase: String?): Boolean {
                phrase?.let { phraseText ->
                    navigateToFragment(fragment =
                        SearchFragment.newInstance(phrase = phraseText, hasAdult = hasAdult)
                    )
                    return true
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            AppState.Loading -> mainProgressBar.progressItem.show()
            is AppState.Success -> {
                mainProgressBar.progressItem.hide()
                movieList = appState.categoryData
                adapter.setData(movieList)
            }
            is AppState.Error -> {
                mainProgressBar.progressItem.hide()
                main.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { loadMovieList() }
            }
        }
    }

    // метод запроса списков с фильмами с сервера
    private fun loadMovieList() {
        // загружаем настройки приложения
        if (loadPreferences()) {            // если настройки изменились:
            viewModel.resetDataLoaded()     // сбрасывам флаг о наличии загруженных данных во viewModel
        }
        // отправляем зарпос на сервер
        viewModel.getMovieListFromServer(hasAdult)
    }

    // метод загрузки настроек приложения из SharedPreferences
    // возвращает true если настройки изменились
    private fun loadPreferences(): Boolean {
        val adultSetting = requireActivity()
            .getPreferences(Context.MODE_PRIVATE)
            .getBoolean(INCLUDE_ADULT_KEY, false)
        if (adultSetting != hasAdult) {
            hasAdult = adultSetting
            return true
        }
        return false
    }

    companion object {
        private const val INCLUDE_ADULT_KEY = "ADULT_KEY"

        fun newInstance() = HomeFragment()
    }
}