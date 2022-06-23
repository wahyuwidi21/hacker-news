package com.codex.hackernews.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Story")
@Parcelize
data class Item(
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("by")
    val by: String,
    @SerializedName("descendants")
    val descendants: Int?,
    @SerializedName("kids")
    val kids: List<Int>?,
    @SerializedName("score")
    var score: Int,
    @SerializedName("time")
    var time: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String?,
    var isFavourite:Boolean?
):Parcelable