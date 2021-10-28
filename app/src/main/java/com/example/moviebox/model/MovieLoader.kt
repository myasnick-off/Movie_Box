package com.example.moviebox.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviebox.model.rest_entities.GenreListDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO
import com.example.moviebox.model.rest_entities.MovieListDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object MovieLoader {

    private const val API_KEY = "02aa4f3b7db68ceb4c5c389d5dee7ce4"

    fun loadGenreList(): GenreListDTO? {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/genre/movie/list?api_key=${API_KEY}&language=ru")
            lateinit var urlConnection: HttpsURLConnection

            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000

                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, GenreListDTO::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadMovieListByGenre(genreIds: IntArray): MovieListDTO? {

        val genreString = arrayToUrlFormat(genreIds)
        try {
            val uri =
                URL(
                    "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=ru&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=${
                        genreString
                    }&with_watch_monetization_types=flatrate"
                )
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000

                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, MovieListDTO::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadMovieDetails(id: Int): MovieDetailsDTO? {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}&language=ru")
            lateinit var urlConnection: HttpsURLConnection

            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                with(urlConnection) {
                    requestMethod = "GET"
                    readTimeout = 10000

                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        getLinesForOld(bufferedReader)
                    } else {
                        getLines(bufferedReader)
                    }
                    return Gson().fromJson(lines, MovieDetailsDTO::class.java)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVal: String?

        while (reader.readLine().also { tempVal = it } != null) {
            rawData.append(tempVal).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun arrayToUrlFormat(genreIds: IntArray): String {
        val urlFormat = StringBuilder()
        for (element in genreIds) {
            urlFormat.append(element)
            if (element != genreIds[genreIds.size - 1]) {
                urlFormat.append("%2C")
            }
        }
        return urlFormat.toString()
    }

}