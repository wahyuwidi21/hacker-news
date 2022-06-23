package com.codex.hackernews.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomConverter {
    @TypeConverter
    fun listKidsToJson(value: List<Int>): String =
        Gson().toJson(value)

    @TypeConverter
    fun jsonToListKids(value: String) =
        Gson().fromJson(value, Array<Int>::class.java)
            .toList()
}