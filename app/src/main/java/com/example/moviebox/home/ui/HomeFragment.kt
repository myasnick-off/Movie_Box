package com.example.moviebox.home.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.OnItemViewClickListener
import com.example.moviebox._core.ui.store.MainStore
import com.example.moviebox._core.ui.store.MainStoreHolder
import com.example.moviebox.databinding.HomeFragmentBinding
import com.example.moviebox.details.ui.DetailsFragment
import com.example.moviebox.filter.ui.FilterFragment
import com.example.moviebox.home.domain.model.Category
import com.example.moviebox.search.ui.SearchFragment
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.navigateToFragment
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel { parametersOf(mainStore) }

    private var storeHolder: MainStoreHolder? = null
    private val mainStore: MainStore by lazy {
        storeHolder?.mainStore ?: throw NullPointerException()
    }

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    // реализация события по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {
        override fun onItemClicked(movieId: Long) {
            navigateToFragment(fragment = DetailsFragment.newInstance(movieId = movieId))
        }
        override fun onItemLongClicked(movie: MovieDTO, view: View) {}
    }
    private val adapter: HomeFragmentAdapter = HomeFragmentAdapter(onMovieItemClickListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storeHolder = context as MainStoreHolder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initStore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() = with(binding) {
        mainRecycler.adapter = adapter
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                isRefreshing = false
                loadMovieList()
            }
        }
        // обработка события по нажатию кнопок меню
        mainToolbar.inflateMenu(R.menu.menu_main)
        mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_filter -> {
                    navigateToFragment(fragment = FilterFragment.newInstance())
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
                    navigateToFragment(fragment = SearchFragment.newInstance(phrase = phraseText))
                    return true
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initStore() {
        mainStore.state.onEach(::renderStoreState).launchIn(viewLifecycleOwner.lifecycleScope)
        mainStore.effect.onEach(::renderStoreEffect).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderStoreState(state: MainStore.State) {
        when (state) {
            MainStore.State.Empty -> showNothing()
            MainStore.State.Loading -> showLoading()
            is MainStore.State.Data -> showContent(data = state.data)
            is MainStore.State.Error -> showError()
            is MainStore.State.Reloading -> showReloading()
        }
    }

    private fun renderStoreEffect(effect: MainStore.Effect) {
        when (effect) {
            is MainStore.Effect.Error -> showErrorSnackBar()
            else -> {}
        }
    }

    private fun showNothing() {
        binding.progressBar.root.hide()
        binding.mainRecycler.hide()
        binding.errorImage.hide()
    }

    private fun showLoading() {
        binding.progressBar.root.show()
        binding.mainRecycler.hide()
        binding.errorImage.hide()
    }

    private fun showError() {
        binding.progressBar.root.hide()
        binding.mainRecycler.hide()
        binding.errorImage.show()
    }

    private fun showContent(data: List<Category>) {
        binding.progressBar.root.hide()
        binding.errorImage.hide()
        binding.mainRecycler.show()
        adapter.setData(data = data)
    }

    private fun showReloading() {
        binding.progressBar.root.show()
        binding.mainRecycler.show()
        binding.errorImage.hide()
    }

    private fun loadMovieList() {
        viewModel.loadData(withAdult = getAdultSettings())
    }

    private fun showErrorSnackBar() {
        binding.root.showSnackBar(
            getString(R.string.error),
            getString(R.string.reload)
        ) { loadMovieList() }
    }

    private fun getAdultSettings(): Boolean {
        return requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_KEY, false)
    }

    companion object {
        private const val ADULT_KEY = "adult_key"

        fun newInstance() = HomeFragment()
    }
}