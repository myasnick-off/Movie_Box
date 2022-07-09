package com.example.moviebox._core.data.remote

import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.filter.ui.model.FilterSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteRepositoryImpl(private val apiService: ApiService) : RemoteRepository {

    override suspend fun getMovieData(id: Long): Result<MovieDetailsDTO> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiService.getMovieDetailsAsync(id).await()
            }
            if (response.isSuccessful) {
                response.body()?.let { return Result.success(value = it) }
            }
            Result.failure(exception = IOException(response.message()))
        } catch (ex: HttpException) {
            return Result.failure(exception = ex)
        } catch (ex: IOException) {
            return Result.failure(exception = ex)
        }
    }

    override suspend fun getMovieListByGenre(withAdult: Boolean, genreId: Int): Result<MovieListDTO> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiService.getMovieListByGenreAsync(
                    includeAdult = withAdult,
                    genreIds = arrayOf(genreId)
                ).await()
            }
            if (response.isSuccessful) {
                response.body()?.let { return Result.success(value = it) }
            }
            Result.failure(exception = IOException(response.message()))
        } catch (ex: HttpException) {
            return Result.failure(exception = ex)
        } catch (ex: IOException) {
            return Result.failure(exception = ex)
        }
    }

    override suspend fun getGenreList(): Result<GenreListDTO> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiService.getGenreListAsync().await()
            }
            if (response.isSuccessful) {
                response.body()?.let { return Result.success(value = it) }
            }
            Result.failure(exception = IOException(response.message()))
        } catch (ex: HttpException) {
            return Result.failure(exception = ex)
        } catch (ex: IOException) {
            return Result.failure(exception = ex)
        }
    }

    override suspend fun searchByPhrase(phrase: String, withAdult: Boolean): Result<MovieListDTO> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiService.getMovieListByPhraseAsync(query = phrase, includeAdult = withAdult).await()
            }
            if (response.isSuccessful) {
                response.body()?.let { return Result.success(value = it) }
            }
            Result.failure(exception = IOException(response.message()))
        } catch (ex: HttpException) {
            return Result.failure(exception = ex)
        } catch (ex: IOException) {
            return Result.failure(exception = ex)
        }
    }

    override suspend fun filterSearch(filterSet: FilterSet, withAdult: Boolean): Result<MovieListDTO> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiService.getMovieListByFilterAsync(
                    includeAdult = withAdult,
                    releaseDateGte = filterSet.yearFrom.toString(),
                    releaseDateLte = filterSet.yearTo.toString(),
                    voteAverageGte = filterSet.ratingFrom,
                    voteAverageLte = filterSet.ratingTo,
                    genreIds = filterSet.genres
                ).await()
            }
            if (response.isSuccessful) {
                response.body()?.let { return Result.success(value = it) }
            }
            Result.failure(exception = IOException(response.message()))
        } catch (ex: HttpException) {
            return Result.failure(exception = ex)
        } catch (ex: IOException) {
            return Result.failure(exception = ex)
        }
    }
}