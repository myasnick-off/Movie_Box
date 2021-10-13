package com.example.moviebox.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.moviebox.databinding.DetailsFragmentBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.model.entities.Movie

class DetailsFragment : Fragment() {

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
        arguments?.getParcelable<Movie>(KEY_BUNDLE)?.let { movie -> setData(movie) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(movieData: Movie) = with(binding) {
        textTitle.text = movieData.title
        textRating.text = movieData.rating_imdb.toString()
        textGenresList.text = movieData.genres.toString().removeSurrounding("[", "]")
        textDirectorName.text = movieData.directors.toString().removeSurrounding("[", "]")
        textDetails.text = movieData.description
        ratingBar.rating = movieData.rating_imdb.toFloat()/2
        // загрузка картинки из Интернета по ее url
        Glide.with(root).load(movieData.poster).into(imgPoster)
        detailsGroup.show()
        detailsProgressBar.hide()
    }

    companion object {
        const val KEY_BUNDLE = "movie_data"

        fun newInstance(bundle: Bundle): DetailsFragment {
            return DetailsFragment().apply { arguments = bundle }
        }
    }
}