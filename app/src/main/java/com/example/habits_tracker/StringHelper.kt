package com.example.habits_tracker

class StringHelper {
    fun getCountWordFor(number: Int): String {
        val lastTwoDigits = getLastTwoDigits(number)

        return when(lastTwoDigits[1]) {
            '2', '3', '4' -> if (lastTwoDigits[0] != '1') "раза" else "раз"
            else -> "раз"
        }
    }

    fun getDaysWordFor(number: Int): String {
        val lastTwoDigits = getLastTwoDigits(number)

        return when(lastTwoDigits[1]) {
            '1' -> "день"
            '2', '3', '4' -> if (lastTwoDigits[0] != '1') "дня" else "дней"
            else -> "дней"
        }
    }

    private fun getLastTwoDigits(number: Int): String {
        var lastTwoDigits = number.toString().apply {
            substring(if (length > 2) length - 2 else 0)
        }
        if (lastTwoDigits.length == 1) {
            lastTwoDigits = "0$lastTwoDigits"
        }
        return lastTwoDigits
    }
}