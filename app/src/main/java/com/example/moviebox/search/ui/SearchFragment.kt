package com.example.moviebox.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.EndOfPageListener
import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox._core.ui.model.ListViewState
import com.example.moviebox.databinding.FragmentSearchBinding
import com.example.moviebox.details.ui.DetailsFragment
import com.example.moviebox.filter.ui.model.FilterSet
import com.example.moviebox.search.ui.adapter.SearchListAdapter
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.navigateToFragment
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var phrase: String? = null
    private var filterSet: FilterSet? = null
    private var withAdult: Boolean = false

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(movieId: Long) {
            navigateToFragment(DetailsFragment.newInstance(movieId))
        }
        override fun onItemLongClicked(movie: MovieDTO, view: View) {}
    }

    private val endOfPageListener = object : EndOfPageListener {
        override fun positionCheck(position: Int, count: Int): Boolean {
            return (position + ITEMS_LEFT) >= count
        }
        override fun loadMoreCallback() {
            viewModel.loadNextPage()
        }
    }

    private val moviesAdapter: SearchListAdapter = SearchListAdapter(itemClickListener, endOfPageListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterSet = it.getParcelable(ARG_FILTER_SET)
            phrase = it.getString(ARG_SEARCH_PHRASE)
            withAdult = it.getBoolean(ARG_WITH_ADULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        searchMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() = with(binding) {
        movieList.adapter = moviesAdapter
    }

    private fun initViewModel() {
        viewModel.viewState.onEach { renderData(it) }.launchIn(lifecycleScope)
        viewModel.viewEffect.onEach { showErrorMessage(it) }.launchIn(lifecycleScope)
    }

    private fun showErrorMessage(message: String) {
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload)
        ) { searchMovies() }
    }

    private fun renderData(viewState: ListViewState) {
        when (viewState) {
            ListViewState.Empty -> showEmptyScreen()
            ListViewState.Loading -> showEmptyLoading()
            is ListViewState.Data -> showData(data = viewState.data, loadMore = viewState.loadMore)
            is ListViewState.Error -> showEmptyScreen()
            is ListViewState.MoreLoading -> showMoreLoading(data = viewState.data)
            is ListViewState.Refreshing -> showRefreshing(data = viewState.data)
        }
    }

    private fun showRefreshing(data: List<RecyclerItem>) = with(binding) {
        movieList.show()
        searchLoader.root.show()
        nothingFoundText.hide()
        updateList(data = data, loadMore = false)
    }

    private fun showMoreLoading(data: List<RecyclerItem>) = with(binding) {
        movieList.show()
        searchLoader.root.show()
        nothingFoundText.hide()
        updateList(data = data, loadMore = false)
    }

    private fun showData(data: List<RecyclerItem>, loadMore: Boolean) = with(binding) {
        movieList.show()
        searchLoader.root.hide()
        nothingFoundText.hide()
        updateList(data = data, loadMore = loadMore)
    }

    private fun showEmptyLoading() = with(binding) {
        movieList.hide()
        searchLoader.root.show()
        nothingFoundText.hide()
    }

    private fun showEmptyScreen() = with(binding) {
        movieList.hide()
        searchLoader.root.hide()
        nothingFoundText.show()
    }

    private fun updateList(data: List<RecyclerItem>, loadMore: Boolean) {
        moviesAdapter.apply {
            this.loadMore = loadMore
            submitList(data)
        }
    }

    private fun searchMovies() {
        phrase?.let { viewModel.searchByPhrase(it, withAdult) }
        filterSet?.let { viewModel.searchByFilter(it, withAdult) }
    }

    companion object {
        private const val ARG_FILTER_SET = "FILTER_SET"
        private const val ARG_SEARCH_PHRASE = "SEARCH_PHRASE"
        private const val ARG_WITH_ADULT = "WITH_ADULT"
        private const val ITEMS_LEFT = 3

        fun newInstance(phrase: String? = null, filterSet: FilterSet? = null) =
            SearchFragment().apply {
                arguments = bundleOf(
                    ARG_SEARCH_PHRASE to phrase,
                    ARG_FILTER_SET to filterSet
                )
            }
    }
}