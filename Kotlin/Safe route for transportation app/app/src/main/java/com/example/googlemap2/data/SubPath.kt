package com.example.googlemap2.data

data class SubPath(
    val distance: Int,
    val door: String,
    val endExitNo: String,
    val endExitX: Double,
    val endExitY: Double,
    val endID: Int,
    val endName: String,
    val endX: Double,
    val endY: Double,
    val lane: List<Lane>,
    val passStopList: PassStopList,
    val sectionTime: Int,
    val startExitNo: String,
    val startExitX: Double,
    val startExitY: Double,
    val startID: Int,
    val startName: String,
    val startX: Double,
    val startY: Double,
    val stationCount: Int,
    val trafficType: Int,
    val way: String,
    val wayCode: Int
)