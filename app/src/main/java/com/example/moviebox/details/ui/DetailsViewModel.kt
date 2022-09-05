package com.example.moviebox.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox.details.domain.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieId: Long,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val localRepository: LocalRepository,
) : ViewModel() {


    private var _viewState: MutableStateFlow<DetailsViewState> = MutableStateFlow(DetailsViewState.Empty)
    val viewState: StateFlow<DetailsViewState> get() = _viewState.asStateFlow()

    init {
        getMovieDetails()
    }

    fun saveMovieToFavorite(movieData: MovieDetailsDTO) {
        viewModelScope.launch {
//            localRepository.saveEntityToFavorite(convertMovieData(movieData))
        }
    }

    fun saveMovieToWishlist(movieData: MovieDetailsDTO) {
//        viewModelScope.launch { localRepository.saveEntityToWishList(convertMovieData(movieData)) }
    }

    fun deleteMovieFromFavorite(movieData: MovieDetailsDTO) {
        //viewModelScope.launch { localRepository.deleteEntityFromFavorite(convertMovieData(movieData)) }
    }

    fun deleteMovieFromWishlist(movieData: MovieDetailsDTO) {
        //viewModelScope.launch { localRepository.deleteEntityFromWishList(convertMovieData(movieData)) }
    }

    fun getMovieDetails() {
        _viewState.value = DetailsViewState.Loading
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId = movieId)
                .onFailure { error ->
                    _viewState.value = DetailsViewState.Error(error = error)
                }
                .onSuccess { data ->
                    _viewState.value = DetailsViewState.Success(movieData = data)
                }
        }
    }
}