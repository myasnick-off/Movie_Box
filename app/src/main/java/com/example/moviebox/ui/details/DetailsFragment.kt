package com.example.moviebox.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviebox.R
import com.example.moviebox.databinding.DetailsFragmentBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.di.showSnackBar
import com.example.moviebox.model.rest.ApiUtils
import com.example.moviebox.model.rest_entities.GenreDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getInt(KEY_BUNDLE)
        val observer = Observer<DetailsAppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        movieId?.let { viewModel.getMovieDetails(it) }
    }

    private fun renderData(appState: DetailsAppState) = with(binding) {
        when (appState) {
            is DetailsAppState.Loading -> detailsProgressBar.show()
            is DetailsAppState.Success -> setData(appState.movieData)
            is DetailsAppState.Error -> {
                detailsProgressBar.hide()
                details.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { movieId?.let { movieId -> viewModel.getMovieDetails(movieId) } }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(movieData: MovieDetailsDTO) = with(binding) {
        textTitle.text = movieData.title
        textRating.text = movieData.voteAverage.toString()
        textDate.text = movieData.releaseDate
        textGenresList.text = genreListToString(movieData.genres)
        textDetails.text = movieData.overview
        ratingBar.rating = movieData.voteAverage.toFloat() / 2

        // загрузка картинки из Интернета по ее url
        val posterUrl = ApiUtils.POSTER_BASE_URL + ApiUtils.POSTER_SIZE_M + movieData.posterPath
//        Glide.with(root).load(posterUrl).into(imgPoster)
//        imgPoster.load(posterUrl)
        Picasso
            .get()
            .load(posterUrl)
            .into(imgPoster)

        detailsGroup.show()
        detailsProgressBar.hide()
    }

    private fun genreListToString(genres: List<GenreDTO>): String {
        val result = StringBuilder()
        for (genre in genres) {
            result.append(genre.name)
            if (genre != genres.last()) {
                result.append(", ")
            }
        }
        return result.toString()
    }

    companion object {
        const val KEY_BUNDLE = "movie_data"

        fun newInstance(bundle: Bundle): DetailsFragment {
            return DetailsFragment().apply { arguments = bundle }
        }
    }
}