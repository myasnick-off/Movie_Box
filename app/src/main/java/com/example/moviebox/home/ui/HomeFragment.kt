package com.example.moviebox.home.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox._core.ui.model.ListViewState
import com.example.moviebox._core.ui.store.MainStore
import com.example.moviebox.databinding.HomeFragmentBinding
import com.example.moviebox.details.ui.DetailsFragment
import com.example.moviebox.filter.ui.FilterFragment
import com.example.moviebox.filter.ui.model.FilterSet
import com.example.moviebox.home.ui.adapter.SublistAdapter
import com.example.moviebox.main.ui.StoreHolder
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

    private val store: MainStore by lazy {
        storeHolder?.mainStore ?: throw NullPointerException()
    }

    private val viewModel: HomeViewModel by viewModel() { parametersOf(store) }

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private var storeHolder: StoreHolder? = null

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(movieId: Long) {
            navigateToFragment(fragment = DetailsFragment.newInstance(movieId = movieId))
        }
        override fun onItemLongClicked(movie: MovieDTO, view: View) {}
    }

    private val gotoClickListener = object : GotoClickListener {
        override fun invoke(categoryId: Int) {
            val filterSet = FilterSet(genres = arrayListOf(categoryId))
            navigateToFragment(fragment = SearchFragment.newInstance(filterSet = filterSet))
        }
    }

    private val adapter: SublistAdapter = SublistAdapter(itemClickListener, gotoClickListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storeHolder = context as StoreHolder
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

    override fun onDetach() {
        storeHolder = null
        super.onDetach()
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
//                    navigateToFragment(fragment = FilterFragment.newInstance())
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
        viewModel.viewState.onEach(::renderStoreState).launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.viewEffect.onEach(::showErrorSnackBar).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderStoreState(state: ListViewState) {
        when (state) {
            is ListViewState.Loading -> showLoading()
            is ListViewState.Data -> showContent(data = state.data)
            is ListViewState.Error -> showError()
            is ListViewState.Refreshing -> showReloading()
            else -> {}
        }
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

    private fun showContent(data: List<RecyclerItem>) {
        binding.progressBar.root.hide()
        binding.errorImage.hide()
        binding.mainRecycler.show()
        adapter.submitList(data)
    }

    private fun showReloading() {
        binding.progressBar.root.show()
        binding.mainRecycler.show()
        binding.errorImage.hide()
    }

    private fun loadMovieList() {
        viewModel.loadData(withAdult = getAdultSettings())
    }

    private fun showErrorSnackBar(message: String) {
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload)
        ) { loadMovieList() }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getAdultSettings(): Boolean {
        return requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_KEY, false)
    }

    companion object {
        private const val ADULT_KEY = "adult_key"

        fun newInstance() = HomeFragment()
    }
}