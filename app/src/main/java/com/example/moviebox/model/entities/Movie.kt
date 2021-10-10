package com.example.moviebox.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val type: String,
    val title: String,
    val poster: String,
    val directors: List<String>,
    val actors: List<String>,
    val genres: List<String>,
    val countries: List<String>,
    val year: Int,
    val description: String,
    val rating_imdb: Double
): Parcelable

fun getDefaultMovie() = Movie(
    "915196",
    "film",
    "Терминатор",
    "https://pbs.twimg.com/media/E1IJSzOXsAIpuro.jpg",
    listOf("Джеймс Кэмерон"),
    listOf("Арнольд Шварценеггер", "Майкл Бин", "Линда Хэмилтон", "Пол Уинфилд", "Лэнс Хенриксен"),
    listOf("фантастика", "боевик", "триллер"),
    listOf("Великобритания", "США"),
    1984,
    "История противостояния солдата Кайла Риза и киборга-терминатора, прибывших в 1984-й год из пост-апокалиптического будущего, где миром правят машины-убийцы, а человечество находится на грани вымирания. Цель киборга: убить девушку по имени Сара Коннор, чей ещё нерождённый сын к 2029 году выиграет войну человечества с машинами. Цель Риза: спасти Сару и остановить Терминатора любой ценой.",
    8.0)

