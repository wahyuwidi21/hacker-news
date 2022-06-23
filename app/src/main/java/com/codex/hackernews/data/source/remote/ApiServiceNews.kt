package com.codex.hackernews.data.source.remote

import com.codex.hackernews.data.model.Comment
import com.codex.hackernews.data.model.Item
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceNews {

    @GET("v0/item/{id}.json?print=pretty")
    fun getStoryItemAsync(
        @Path("id") page: Int,
        @Query("print") order: String = "pretty"
    ): Deferred<Item>

    @GET("v0/item/{id}.json?print=pretty")
    fun getCommentItemAsync(
        @Path("id") page: Int,
        @Query("print") order: String = "pretty"
    ): Deferred<Comment>

    @GET("v0/topstories.json?print=pretty")
    fun getTopStoriesAsync(
    ): Deferred<List<Int>>

}