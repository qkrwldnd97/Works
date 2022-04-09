package com.example.googlemap2.data

data class shortestpath(
    val busCount: Int,
    val endRadius: Int,
    val outTrafficCheck: Int,
    val path: List<Path>,
    val pointDistance: Int,
    val searchType: Int,
    val startRadius: Int,
    val subwayBusCount: Int,
    val subwayCount: Int
)