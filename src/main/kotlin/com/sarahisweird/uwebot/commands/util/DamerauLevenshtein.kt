package com.sarahisweird.uwebot.commands.util

object DamerauLevenshtein {
    fun of(str1: String, str2: String): Int {
        val d = Array(str1.length + 1) { Array(str2.length + 1) { 0 } }

        (0..str1.length).forEach { d[it][0] = it }
        (0..str2.length).forEach { d[0][it] = it }

        (1..str1.length).forEach { i ->
            (1..str2.length).forEach { j ->
                val cost = if (str1[i - 1] == str2[j - 1]) 0 else 1

                d[i][j] = minOf(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost)

                if (i > 1 && j > 1 && str1[i - 1] == str2[j - 2] && str1[i - 2] == str2[j - 1])
                    d[i][j] = minOf(d[i][j], d[i - 2][j - 2] + 1)
            }
        }

        return d[str1.length][str2.length]
    }
}