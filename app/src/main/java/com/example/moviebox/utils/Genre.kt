package com.example.moviebox.utils

import com.example.moviebox.R

enum class Genre(val id: Int, val nameResId: Int) {
    ACTION(id = 28, nameResId = R.string.action),
    ADVENTURE(id = 12, nameResId = R.string.adventure),
    ANIMATION(id = 16, nameResId = R.string.animation),
    COMEDY(id = 35, nameResId = R.string.comedy),
    CRIME(id = 80, nameResId = R.string.crime),
    DOCUMENTARY(id = 99, nameResId = R.string.documentary),
    DRAMA(id = 18, nameResId = R.string.drama),
    FAMILY(id = 10751, nameResId = R.string.family),
    FANTASY(id = 14, nameResId = R.string.fantasy),
    HISTORY(id = 36, nameResId = R.string.history),
    HORROR(id = 27, nameResId = R.string.horror),
    MUSIC(id = 10402, nameResId = R.string.music),
    MYSTERY(id = 9648, nameResId = R.string.mystery),
    ROMANCE(id = 10749, nameResId = R.string.romance),
    SCIENCE_FICTION(id = 878, nameResId = R.string.science_fiction),
    TV_MOVIE(id = 10770, nameResId = R.string.tv_movie),
    THRILLER(id = 53, nameResId = R.string.thriller),
    WAR(id = 10752, nameResId = R.string.war),
    WESTERN(id = 37, nameResId = R.string.western)
}