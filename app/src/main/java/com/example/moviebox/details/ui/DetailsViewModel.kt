package com.example.moviebox.details.ui

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
) : ViewModel() {


    private val liveData = MutableLiveData<DetailsAppState>()

    fun getLiveData(): LiveData<DetailsAppState> = liveData

    fun saveMovieToFavorite(movieData: MovieDetailsDTO) {
        viewModelScope.launch {
            localRepository.saveEntityToFavorite(convertMovieData(movieData))
        }
    }

    fun saveMovieToWishlist(movieData: MovieDetailsDTO) {
        viewModelScope.launch { localRepository.saveEntityToWishList(convertMovieData(movieData)) }
    }

    fun deleteMovieFromFavorite(movieData: MovieDetailsDTO) {
        viewModelScope.launch { localRepository.deleteEntityFromFavorite(convertMovieData(movieData)) }
    }

    fun deleteMovieFromWishlist(movieData: MovieDetailsDTO) {
        viewModelScope.launch { localRepository.deleteEntityFromWishList(convertMovieData(movieData)) }
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
                    localRepository.saveEntityToHistory(convertMovieData(data = data))
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