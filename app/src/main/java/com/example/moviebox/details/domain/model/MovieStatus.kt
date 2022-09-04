package com.example.moviebox.details.domain.model

enum class MovieStatus(val code: String) {
    Unknown(code = "Unknown"),
    Rumored(code = "Rumored"),
    Planned(code = "Planned"),
    InProduction(code = "In Production"),
    PostProduction(code = "Post Production"),
    Released(code = "Released"),
    Canceled(code = "Canceled");

    companion object {
        fun findOrDefault(code: String) : MovieStatus {
            return values().find { it.code == code} ?: Unknown
        }
    }
}