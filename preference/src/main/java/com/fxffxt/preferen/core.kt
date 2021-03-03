package com.fxffxt.preferen

fun String?.isNullOrEmptyByCandidate(candidate: String): String {
    return if (this.isNullOrBlank()) candidate else this
}