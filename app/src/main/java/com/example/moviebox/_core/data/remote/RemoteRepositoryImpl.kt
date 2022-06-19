package com.example.moviebox._core.data.remote

import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.ui.model.FilterSet

class RemoteRepositoryImpl(private val remoteDataSource: RemoteDataSource) : RemoteRepository {

    override fun getMovieData(id: Long): MovieDetailsDTO? {
        return remoteDataSource.apiService.getMovieDetails(id).execute().body()
    }

    override fun getMovieListByGenre(withAdult: Boolean, genreId: Int): MovieListDTO? {
        return remoteDataSource.apiService
            .getMovieListByGenre(includeAdult = withAdult, genreIds = arrayOf(genreId))
            .execute()
            .body()
    }

    override fun getGenreList(): GenreListDTO? {
        return remoteDataSource.apiService.getGenreList().execute().body()
    }

    override fun searchByPhrase(phrase: String, withAdult: Boolean): List<MovieDTO>? {
        return remoteDataSource.apiService
            .getMovieListByPhrase(query = phrase, includeAdult = withAdult)
            .execute()
            .body()?.results
    }

    override fun filterSearch(filterSet: FilterSet, withAdult: Boolean): List<MovieDTO>? {
        return remoteDataSource.apiService
            .getMovieListByFilter(
                includeAdult = withAdult,
                releaseDateGte = filterSet.yearFrom.toString(),
                releaseDateLte = filterSet.yearTo.toString(),
                voteAverageGte = filterSet.ratingFrom,
                voteAverageLte = filterSet.ratingTo,
                genreIds = filterSet.genres
            )
            .execute()
            .body()?.results
    }
}