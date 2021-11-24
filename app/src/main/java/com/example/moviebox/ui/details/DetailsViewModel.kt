package com.example.moviebox.ui.details

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.App.Companion.getDataBase
import com.example.moviebox.model.repository.Repository
import com.example.moviebox.model.rest_entities.MovieDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO
import com.example.moviebox.room.LocalRepository
import com.example.moviebox.room.LocalRepositoryImpl

class DetailsViewModel(
    private val repository: Repository,
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
            val movieData = repository.getMovieDataFromServer(id)
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