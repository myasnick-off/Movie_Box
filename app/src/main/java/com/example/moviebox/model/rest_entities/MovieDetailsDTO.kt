package com.example.moviebox.model.rest_entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsDTO(
    val genres: ArrayList<GenreDTO>,
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Number
) : Parcelable {

    companion object {

        fun getDefaultDetails() = MovieDetailsDTO(
            arrayListOf(GenreDTO(1,"Фантастика"), GenreDTO(2, "Приключения")),
            1234,
            "Наследник знаменитого дома Атрейдесов Пол отправляется вместе с семьей на одну из самых опасных планет во Вселенной — Арракис. Здесь нет ничего, кроме песка, палящего солнца, гигантских чудовищ и основной причины межгалактических конфликтов — невероятно ценного ресурса, который называется меланж. В результате захвата власти Пол вынужден бежать и скрываться, и это становится началом его эпического путешествия. Враждебный мир Арракиса приготовил для него множество тяжелых испытаний, но только тот, кто готов взглянуть в глаза своему страху, достоин стать избранным.",
            "",
            "10.09.2021",
            "Дюна",
            7.9
        )
    }
}


