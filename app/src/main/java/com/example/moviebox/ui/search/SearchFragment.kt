package com.example.moviebox.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentSearchBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.di.showSnackBar
import com.example.moviebox.model.entities.FilterSet
import com.example.moviebox.model.rest_entities.MovieDTO
import com.example.moviebox.ui.OnItemViewClickListener
import com.example.moviebox.ui.details.DetailsFragment
import com.example.moviebox.ui.profile.ProfileAppState
import com.example.moviebox.ui.profile.TabRecyclerAdapter

class SearchFragment : Fragment() {

    // создаем/вызываем экземпляр SearchViewModel
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var phrase: String? = null
    private var filterSet: FilterSet? = null
    private var withAdult: Boolean = false
    private lateinit var adapter: TabRecyclerAdapter

    // реализация событий по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {

        //по короткому нажатию запускаем фрагмент с деталями фильма
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        searchRecycler.layoutManager = GridLayoutManager(context, 2)
        adapter = TabRecyclerAdapter(onMovieItemClickListener)
        searchRecycler.adapter = adapter

        val observer = Observer<ProfileAppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        searchMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: ProfileAppState) = with(binding) {
        when (appState) {
            ProfileAppState.Loading -> searchProgressBar.show()
            is ProfileAppState.Success -> {
                searchProgressBar.hide()
                showResult(appState.movieList)
            }
            is ProfileAppState.Error -> {
                searchProgressBar.hide()
                searchLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { searchMovies() }
            }
        }
    }

    private fun searchMovies() {
        if (phrase != null) {
            viewModel.searchRequest(phrase!!, withAdult)
        }
        if(filterSet != null) {
            viewModel.filterSearchRequest(filterSet!!, withAdult)
        }
    }

    private fun showResult(movieList: List<MovieDTO>) {
        if (movieList.isNotEmpty()) {
            adapter.submitList(movieList)
        } else {
            binding.cantFindText.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val ARG_FILTER_SET = "FILTER_SET"
        private const val ARG_SEARCH_PHRASE = "SEARCH_PHRASE"
        private const val ARG_WITH_ADULT = "WITH_ADULT"

        fun newInstance(bundle: Bundle) =
            SearchFragment().apply {
                arguments = bundle
            }
    }
}