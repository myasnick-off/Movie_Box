package com.example.moviebox.details.ui

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.App.Companion.getDataBase
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox._core.data.local.LocalRepositoryImpl

class DetailsViewModel(
    private val remoteRepository: RemoteRepository,
    private val profileRepository: LocalRepository = LocalRepositoryImpl(getDataBase()),
    handlerThread: HandlerThread = HandlerThread("saveDeleteThread")
) : ViewModel() {

    init {
        handlerThread.start()
    }

    private val hd = handlerThread
    private val handler = Handler(handlerThread.looper)
    private val liveData = MutableLiveData<DetailsAppState>()

    fun getLiveData(): LiveData<DetailsAppState> = liveData

    fun saveMovieToFavorite(movieData: MovieDetailsDTO) {
        handler.post { profileRepository.saveEntityToFavorite(convertMovieData(movieData)) }
    }

    fun saveMovieToWishlist(movieData: MovieDetailsDTO) {
        handler.post { profileRepository.saveEntityToWishList(convertMovieData(movieData)) }
    }

    fun deleteMovieFromFavorite(movieData: MovieDetailsDTO) {
        handler.post { profileRepository.deleteEntityFromFavorite(convertMovieData(movieData)) }
    }

    fun deleteMovieFromWishlist(movieData: MovieDetailsDTO) {
        handler.post { profileRepository.deleteEntityFromWishList(convertMovieData(movieData)) }
    }

    fun getMovieDetailsFromServer(id: Long) {
        liveData.value = DetailsAppState.Loading
        Thread {
            val movieData = remoteRepository.getMovieData(id)
            if (movieData != null) {
                liveData.postValue(DetailsAppState.Success(movieData))
                // сохраняем данные о фильме в локальную БД
                profileRepository.saveEntityToHistory(convertMovieData(movieData))
            } else {
                liveData.postValue(DetailsAppState.Error(Throwable()))
            }
        }.start()
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

    override fun onCleared() {
        hd.quit()
        super.onCleared()
    }
}