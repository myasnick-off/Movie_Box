package com.example.moviebox.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.moviebox.R
import com.example.moviebox.databinding.DetailsFragmentBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.di.showSnackBar
import com.example.moviebox.model.rest.ApiUtils
import com.example.moviebox.model.rest_entities.GenreDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()
    private val localViewModel: LocalDataViewModel by lazy {
        ViewModelProvider(this)[LocalDataViewModel::class.java]
    }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private var movieId: Long? = null
    private var inFavorite: Boolean = false
    private var inWishlist: Boolean = false
    private lateinit var movieData: MovieDetailsDTO

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
        movieId = arguments?.getLong(KEY_BUNDLE)

        val observer = Observer<DetailsAppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        movieId?.let { viewModel.getMovieDetailsFromServer(it) }

        val localObserver = Observer<LocalDataAppState> { renderCheck(it) }
        localViewModel.getLiveData().observe(viewLifecycleOwner, localObserver)
        movieId?.let { localViewModel.checkInLocalDB(it) }

        // обработка события по нажатию кнопок меню
        binding.detailsToolbar.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.action_favorite -> {
                    if (inFavorite) {
                        viewModel.deleteMovieFromFavorite(movieData)
                        it.setIcon(R.drawable.ic_baseline_favorite_border_24)
                    } else {
                        viewModel.saveMovieToFavorite(movieData)
                        it.setIcon(R.drawable.ic_baseline_favorite_24)
                    }
                    movieId?.let { localViewModel.checkInLocalDB(movieId!!) }
                    true
                }
                R.id.action_wishlist -> {
                    if (inWishlist) {
                        viewModel.deleteMovieFromWishlist(movieData)
                        it.setIcon(R.drawable.ic_baseline_bookmark_border_24)
                    } else {
                        viewModel.saveMovieToWishlist(movieData)
                        it.setIcon(R.drawable.ic_baseline_bookmark_24)
                    }
                    movieId?.let { localViewModel.checkInLocalDB(movieId!!) }
                    true
                }
                else -> false
            }
        }
    }

    private fun renderCheck(appState: LocalDataAppState?) = with(binding) {
        when (appState) {
            is LocalDataAppState.Loading -> detailsProgressBar.show()
            is LocalDataAppState.Success -> {
                inFavorite = appState.hasMovie[0]
                inWishlist = appState.hasMovie[1]
                if (inFavorite) {
                    detailsToolbar.menu.findItem(R.id.action_favorite)
                        .setIcon(R.drawable.ic_baseline_favorite_24)
                }
                if (inWishlist) {
                    detailsToolbar.menu.findItem(R.id.action_wishlist)
                        .setIcon(R.drawable.ic_baseline_bookmark_24)
                }
                detailsProgressBar.hide()
            }
            else -> detailsProgressBar.hide()
        }
    }

    private fun renderData(appState: DetailsAppState) = with(binding) {
        when (appState) {
            DetailsAppState.Loading -> detailsProgressBar.show()
            // при успешной загрузке фильма:
            is DetailsAppState.Success -> {
                movieData = appState.movieData
                // заполняем фрагмент данными фильма
                setData(movieData)
                // подключаем кнопки меню к Toolbar
//                detailsToolbar.inflateMenu(R.menu.menu_details)
                detailsProgressBar.hide()
            }
            is DetailsAppState.Error -> {
                detailsProgressBar.hide()
                detailsLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { movieId?.let { movieId -> viewModel.getMovieDetailsFromServer(movieId) } }
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
        imgPoster.load(posterUrl)

// Другие способы загрузки изображений
//        Glide.with(root).load(posterUrl).into(imgPoster)
//        Picasso
//            .get()
//            .load(posterUrl)
//            .into(imgPoster)

        detailsGroup.show()
        if (!movieData.adult) imageAdult.hide()
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