package com.adventofcode

import java.io.InputStream

val numberRegex = Regex("\\d+")

fun readFileFromResources(fileName: String): String? {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream: InputStream? = classLoader.getResourceAsStream(fileName)
    return inputStream?.bufferedReader()?.use { it.readText() }
}
