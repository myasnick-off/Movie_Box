package com.example.moviebox.details.ui

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox.details.domain.MovieDetailsUseCase
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val localRepository: LocalRepository,
    handlerThread: HandlerThread = HandlerThread("saveDeleteThread")
) : ViewModel() {

    init {
        handlerThread.start()
    }

    private val handler = Handler(handlerThread.looper)
    private val liveData = MutableLiveData<DetailsAppState>()

    fun getLiveData(): LiveData<DetailsAppState> = liveData

    fun saveMovieToFavorite(movieData: MovieDetailsDTO) {
        handler.post { localRepository.saveEntityToFavorite(convertMovieData(movieData)) }
    }

    fun saveMovieToWishlist(movieData: MovieDetailsDTO) {
        handler.post { localRepository.saveEntityToWishList(convertMovieData(movieData)) }
    }

    fun deleteMovieFromFavorite(movieData: MovieDetailsDTO) {
        handler.post { localRepository.deleteEntityFromFavorite(convertMovieData(movieData)) }
    }

    fun deleteMovieFromWishlist(movieData: MovieDetailsDTO) {
        handler.post { localRepository.deleteEntityFromWishList(convertMovieData(movieData)) }
    }

    fun getMovieDetailsFromServer(movieId: Long) {
        liveData.value = DetailsAppState.Loading
        viewModelScope.launch {
            movieDetailsUseCase(movieId = movieId)
                .onFailure { error ->
                    liveData.value = DetailsAppState.Error(error = error)
                }
                .onSuccess { data ->
                    liveData.value = DetailsAppState.Success(movieData = data)
                }
        }
    }

    private fun convertMovieData(data: MovieDetailsDTO): MovieDTO {
        return MovieDTO(
            data.id,
            data.posterPath,
            data.releaseDate,
            data.title,
            data.voteAverage,
            data.adult
        )
    }
}