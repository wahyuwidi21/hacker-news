package com.codex.hackernews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Story")
data class Comment(
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("by")
    val by: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("kids")
    val kids: List<Int>?,
    @SerializedName("time")
    var time: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("parent")
    val parent: Int,
)