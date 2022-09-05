package com.example.moviebox.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.ApiUtils
import com.example.moviebox._core.data.remote.model.GenreDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox.databinding.DetailsFragmentBinding
import com.example.moviebox.details.domain.model.MovieDetails
import com.example.moviebox.utils.hide
import com.example.moviebox.utils.show
import com.example.moviebox.utils.showSnackBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private val movieId: Long? by lazy {
        requireArguments().getLong(ARG_MOVIE_ID)
    }

    private val viewModel: DetailsViewModel by viewModel() { parametersOf(movieId)}

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

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
        setHasOptionsMenu(true)
        initView()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() = with(binding) {
        detailsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_favorite -> {
                    true
                }
                R.id.action_wishlist -> {

                    true
                }
                R.id.action_share -> {
                    //navigateToFragment(fragment = ContactsFragment.newInstance(movieData))
                    true
                }
                else -> false
            }
        }
        // анимация при скроллинге ListView
        detailsLayout.setOnScrollChangeListener { _, _, _, _, _ ->
            val params = detailsLayout.layoutParams as CoordinatorLayout.LayoutParams
            // постепенно убираем верхний отступ у ListView при его скроллинге вниз
            if (detailsLayout.canScrollVertically(+1) && params.topMargin >= 0) {
                params.topMargin -= 2
                detailsLayout.layoutParams = params
            }
            // возвращаем верхний отступ у ListView при завершении скролинга вверх
            if (!detailsLayout.canScrollVertically(-1)) {
                params.topMargin = 60
                detailsLayout.layoutParams = params
            }
            // добавляем тень у AppBar, когда элеиенты ListView при скроллинге заезжают под него
            detailsAppbar.isSelected = detailsLayout.canScrollVertically(-1)
        }
    }

    private fun initViewModel() {
        viewModel.viewState.onEach { renderData(it) }.launchIn(lifecycleScope)
    }

    private fun renderData(appState: DetailsViewState) = with(binding) {
        when (appState) {
            DetailsViewState.Empty -> {
                detailsLoader.hide()
                detailsLayout.hide()
            }
            DetailsViewState.Loading -> {
                detailsLoader.show()
                detailsLayout.hide()
            }
            is DetailsViewState.Success -> {
                detailsLoader.hide()
                detailsLayout.show()
                setData(data = appState.movieData)
            }
            is DetailsViewState.Error -> {
                detailsLoader.hide()
                detailsLayout.hide()
                detailsLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { movieId?.let { viewModel.getMovieDetails() } }
            }
        }
    }

    private fun setData(data: MovieDetails) = with(binding) {
        movieTitle.text = data.title
        movieRating.text = data.voteAverage.toString()
        releaseDate.text = data.releaseDate
        movieGenres.text = genreListToString(data.genres)
        textDetails.text = data.overview
        ratingBar.rating = data.voteAverage.toFloat() / 2
        imageAdult.isVisible = data.adult
        // загрузка картинки из Интернета по ее url
        val posterUrl = ApiUtils.POSTER_BASE_URL + ApiUtils.POSTER_SIZE_M + data.posterPath
        imgPoster.load(posterUrl)
    }

    private fun genreListToString(genres: List<String>): String {
        val result = StringBuilder()
        for (genre in genres) {
            result.append(genre)
            if (genre != genres.last()) {
                result.append(", ")
            }
        }
        return result.toString()
    }

    companion object {
        const val ARG_MOVIE_ID = "movie_id"

        fun newInstance(movieId: Long) =
            DetailsFragment().apply {
                arguments = bundleOf(ARG_MOVIE_ID to movieId)
            }
    }
}