package com.codex.hackernews.data.repository

import com.codex.hackernews.data.model.Item
import com.codex.hackernews.data.source.local.StoryDao
import com.codex.hackernews.data.source.remote.ApiServiceNews
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.lang.Exception
import javax.inject.Inject

open class StoryRepository @Inject constructor(
    private val apiServiceNews: ApiServiceNews,
    private val storyDao: StoryDao
) {

    private suspend fun getTopStoriesRemote(): List<Int> {
        return apiServiceNews.getTopStoriesAsync().await()
    }

    private suspend fun getStoryItem(id: Int): Item {
        return apiServiceNews.getStoryItemAsync(id).await()
    }

    suspend fun getListStoryItem(): List<Item> {
//        try {
            val topStories = getTopStoriesRemote()
           return topStories.asFlow().map { id ->
               val item = getStoryItem(id)
                storyDao.insertStory(item)
                item
            }.toList()


//        } catch (e: Exception) {
//            return storyDao.selectAllStory()
//        }
    }
}