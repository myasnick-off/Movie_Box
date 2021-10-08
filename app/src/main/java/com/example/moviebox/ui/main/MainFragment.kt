package com.example.moviebox.ui.main

import android.R
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.moviebox.AppState
import com.example.moviebox.databinding.MainFragmentBinding
import com.example.moviebox.model.entities.Movie
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Error


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                groupDetails.visibility = View.VISIBLE
                setData(appState.movieData)
            }
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
                groupDetails.visibility = View.INVISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                groupDetails.visibility = View.INVISIBLE
                Snackbar
                    .make(main, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovie() }
                    .show()
            }
        }
    }

    private fun setData(movieData: Movie) = with(binding) {
        textTitle.text = movieData.title
        textRating.text = movieData.rating.toString()
        textDetails.text = movieData.details

        Glide.with(this@MainFragment).load(movieData.posterUrl).into(imgPoster)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}