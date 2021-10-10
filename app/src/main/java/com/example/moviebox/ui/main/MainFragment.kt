package com.example.moviebox.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebox.AppState
import com.example.moviebox.R
import com.example.moviebox.databinding.MainFragmentBinding
import com.example.moviebox.model.entities.Movie
import com.example.moviebox.ui.adapters.MainFragmentAdapter
import com.example.moviebox.ui.details.DetailsFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null

    // реализация события по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {
        override fun onItemClicked(movie: Movie) {
            val manager = activity?.supportFragmentManager
            manager?.let {
                val bundle = Bundle().apply {
                    putParcelable(DetailsFragment.KEY_BUNDLE, movie)
                }
                manager
                    .beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("detailsFragment")
                    .commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecycler.adapter = adapter
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMovieList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mainProgressBar.visibility = View.GONE
                mainRecycler.layoutManager = GridLayoutManager(context, 2)
                adapter = MainFragmentAdapter(onMovieItemClickListener).apply {
                    setMovieList(appState.movieData)
                }
                mainRecycler.adapter = adapter
            }
            is AppState.Loading -> {
                mainProgressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainProgressBar.visibility = View.GONE
                Snackbar
                    .make(main, R.string.error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.reload) { viewModel.getMovieList() }
                    .show()
            }
        }
    }


    interface OnItemViewClickListener {
        fun onItemClicked(movie: Movie)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}