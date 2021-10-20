package com.example.moviebox.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.moviebox.ui.adapters.MainFragmentAdapter
import com.example.moviebox.ui.details.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null

    // реализация события по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {
        override fun onItemClicked(movieId: Int) {
            val manager = activity?.supportFragmentManager
            // передаем во фрагмент с деталями фильма его ID
            manager?.let {
                val bundle = Bundle().apply {
                    putInt(DetailsFragment.KEY_BUNDLE, movieId)
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
            is AppState.Loading -> mainProgressBar.show()
            is AppState.Success -> {
                mainProgressBar.hide()
                mainRecycler
                    .layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                adapter = MainFragmentAdapter(onMovieItemClickListener).apply {
                    setData(appState.categoryData)
                }
                mainRecycler.adapter = adapter
            }
            is AppState.Error -> {
                mainProgressBar.hide()
                main.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { viewModel.getMovieList() }
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemClicked(movieId: Int)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}