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
) : Parcelable {

    companion object {

        fun getDefaultLatest() = Movie(
            "915195",
            "film",
            "Дюна",
            "https://mamasgeeky.com/wp-content/uploads/2021/09/dune-movie-poster.jpg",
            listOf("Дени Вильнёв"),
            listOf(
                "Тимоти Шаламе",
                "Ребекка Фергюсон",
                "Оскар Айзек",
                "Джош Бролин",
                "Джейсон Момоа"
            ),
            listOf("фантастика", "боевик", "драма", "приключения"),
            listOf("США", "Канада", "Венгрия"),
            2021,
            "Наследник знаменитого дома Атрейдесов Пол отправляется вместе с семьей на одну из самых опасных планет во Вселенной — Арракис. Здесь нет ничего, кроме песка, палящего солнца, гигантских чудовищ и основной причины межгалактических конфликтов — невероятно ценного ресурса, который называется меланж. В результате захвата власти Пол вынужден бежать и скрываться, и это становится началом его эпического путешествия. Враждебный мир Арракиса приготовил для него множество тяжелых испытаний, но только тот, кто готов взглянуть в глаза своему страху, достоин стать избранным.",
            7.9
        )

        fun getDefaultThriller() = Movie(
            "915196",
            "film",
            "Терминатор",
            "https://pbs.twimg.com/media/E1IJSzOXsAIpuro.jpg",
            listOf("Джеймс Кэмерон"),
            listOf(
                "Арнольд Шварценеггер",
                "Майкл Бин",
                "Линда Хэмилтон",
                "Пол Уинфилд",
                "Лэнс Хенриксен"
            ),
            listOf("фантастика", "боевик", "триллер"),
            listOf("Великобритания", "США"),
            1984,
            "История противостояния солдата Кайла Риза и киборга-терминатора, прибывших в 1984-й год из пост-апокалиптического будущего, где миром правят машины-убийцы, а человечество находится на грани вымирания. Цель киборга: убить девушку по имени Сара Коннор, чей ещё нерождённый сын к 2029 году выиграет войну человечества с машинами. Цель Риза: спасти Сару и остановить Терминатора любой ценой.",
            8.0
        )

        fun getDefaultComedy() = Movie(
            "915197",
            "film",
            "Карты, деньги, два ствола",
            "https://www.film.ru/sites/default/files/movies/posters/1621498-1583770.jpeg",
            listOf("Гай Ричи"),
            listOf(
                "Джейсон Флеминг",
                "Ник Моран",
                "Джейсон Стэйтем",
                "Стивен Макинтош",
                "Николас Роу"
            ),
            listOf("комедия", "боевик", "криминал"),
            listOf("Великобритания"),
            1998,
            "Четверо молодых парней накопили каждый по 25 тысяч фунтов, чтобы один из них мог сыграть в карты с опытным шулером и матерым преступником, известным по кличке Гарри-Топор. Парень в итоге проиграл 500 тысяч, на уплату долга ему дали неделю.\n" +
                    "\n" +
                    "В противном случае и ему и его «спонсорам» каждый день будут отрубать по пальцу, а потом... Чтобы выйти из положения, ребята решили ограбить бандитов, решивших ограбить трех «ботаников», выращивающих марихуану для местного наркобарона. Но на этом приключения четверки не заканчиваются...",
            8.6
        )

        fun getDefaultMovieList(size: Int, category: CategoryName): List<Movie> {
            val movies = arrayListOf<Movie>()
            for(i in 1..size) {
                when(category) {
                    CategoryName.LATEST -> movies.add(getDefaultLatest())
                    CategoryName.COMEDY -> movies.add(getDefaultComedy())
                    CategoryName.THRILLER -> movies.add(getDefaultThriller())
                    else -> movies.clear()
                }
            }
            return movies
        }
    }
}



