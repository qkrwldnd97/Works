package com.example.googlemap2.data

data class Path(
    val info: Info,
    val pathType: Int,
    val subPath: List<SubPath>
)