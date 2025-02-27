package com.example.myapplication.utils

internal fun String.getInitials(): String {
    val words = this.trim()
        .split("\\s+".toRegex())
        .filter { it.isNotEmpty() }

    return when {
        words.isEmpty() -> ""
        words.size == 1 -> words[0].take(1).uppercase()
        else -> words[0].take(1).uppercase() + words[1].take(1).uppercase()
    }
}

internal fun String.getNumberPokemon(): Int {
    return this.substringAfter("pokemon/").substringBefore("/").toInt()
}