package com.imtae.gitcheck.retrofit.domain

data class Contribution (
    val years: ArrayList<Year>? = null,
    val contributions: ArrayList<Contributions>? = null
)

data class Year (
    val year: String? = "",
    val total: Int? = 0,
    val range: Range? = null
)

data class Range(
    val start: String? = null,
    val end: String? = null
)

data class Contributions (
    val date : String? = null,
    val count : Int = 0,
    val color : String? = null,
    val intensity : Int = 0
)