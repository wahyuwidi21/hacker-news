package com.codex.hackernews.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun millisToDate(millis:Long):String{
    val formatter = SimpleDateFormat("dd/MM/yyyy");
    return formatter.format(
        Date(millis))
}

@SuppressLint("SimpleDateFormat")
fun millisToDateTime(millis:Long):String{
    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm");
    return formatter.format(
        Date(millis))
}