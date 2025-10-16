package com.adventofcode.yr2015

import java.security.MessageDigest

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val md = MessageDigest.getInstance("MD5")
    val secret = "iwrupvqb"
    var counter = 1

    while (true) {
        val input = secret + "$counter"
        val digest = md.digest(input.toByteArray())
        if (digest.toHexString().startsWith("000000")) {
            break
        }
        counter++
    }

    println("Result Counter $counter")
}