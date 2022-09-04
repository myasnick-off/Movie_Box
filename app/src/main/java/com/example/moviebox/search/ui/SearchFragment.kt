package com.example.moviebox.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentSearchBinding
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import com.example.moviebox.filter.ui.model.FilterSet
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox.details.ui.DetailsFragment
import com.example.moviebox.profile.ui.ProfileAppState
import com.example.moviebox.profile.ui.TabRecyclerAdapter
import com.example.moviebox.utils.navigateToFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var phrase: String? = null
    private var filterSet: FilterSet? = null
    private var withAdult: Boolean = false

    private val onMovieItemClickListener = object : ItemClickListener {
        override fun onItemClicked(movieId: Long) {
            navigateToFragment(DetailsFragment.newInstance(movieId))
        }
        override fun onItemLongClicked(movie: MovieDTO, view: View) {}
    }

    private val moviesAdapter: TabRecyclerAdapter = TabRecyclerAdapter(onMovieItemClickListener)

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
        searchRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = moviesAdapter
        }
    }

    private fun initViewModel() {
        val observer = Observer<ProfileAppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(appState: ProfileAppState) = with(binding) {
        when (appState) {
            ProfileAppState.Loading -> searchProgressBar.root.show()
            is ProfileAppState.Success -> {
                searchProgressBar.root.hide()
                showResult(appState.movieList)
            }
            is ProfileAppState.Error -> {
                searchProgressBar.root.hide()
                searchLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { searchMovies() }
            }
        }
    }

    private fun searchMovies() {
        phrase?.let { viewModel.searchRequest(it, withAdult) }
        filterSet?.let { viewModel.filterSearchRequest(it, withAdult) }
    }

    private fun showResult(movieList: List<MovieDTO>) {
        if (movieList.isNotEmpty()) {
            moviesAdapter.submitList(movieList)
        } else {
            binding.cantFindText.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val ARG_FILTER_SET = "FILTER_SET"
        private const val ARG_SEARCH_PHRASE = "SEARCH_PHRASE"
        private const val ARG_WITH_ADULT = "WITH_ADULT"

        fun newInstance(phrase: String? = null, filterSet: FilterSet? = null) =
            SearchFragment().apply {
                arguments = bundleOf(
                    ARG_SEARCH_PHRASE to phrase,
                    ARG_FILTER_SET to filterSet
                )
            }
    }
}