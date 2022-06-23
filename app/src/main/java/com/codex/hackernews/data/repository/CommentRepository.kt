package com.codex.hackernews.data.repository

import com.codex.hackernews.data.model.Comment
import com.codex.hackernews.data.model.Item
import com.codex.hackernews.data.source.remote.ApiServiceNews
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

open class CommentRepository @Inject constructor(
    private val apiServiceNews: ApiServiceNews,
) {


    private suspend fun getComments(id:Int):Comment {
        return apiServiceNews.getCommentItemAsync(id).await()
    }

    suspend fun getListStoryItem(childList: List<Int>): List<Comment> {
        return childList.asFlow().map { id ->
            getComments(id)
        }.toList()
    }
}