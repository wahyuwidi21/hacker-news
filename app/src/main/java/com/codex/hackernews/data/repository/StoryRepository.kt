package com.codex.hackernews.data.repository

import com.codex.hackernews.data.model.Item
import com.codex.hackernews.data.source.local.PreferencesManager
import com.codex.hackernews.data.source.local.StoryDao
import com.codex.hackernews.data.source.remote.ApiServiceNews
import com.codex.hackernews.utils.isNetworkConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

open class StoryRepository @Inject constructor(
    private val apiServiceNews: ApiServiceNews,
    private val storyDao: StoryDao,
) {

    private suspend fun getTopStoriesRemote(): List<Int> {
        return apiServiceNews.getTopStoriesAsync().await()
    }

    private suspend fun getStoryItem(id: Int): Item {
        return apiServiceNews.getStoryItemAsync(id).await()
    }

    suspend fun setItemFavourite(item: Item, isFavourite: Boolean): Boolean {
        val result = withContext(Dispatchers.IO) {
            item.isFavourite = isFavourite
            storyDao.updateFavourite(item)
            return@withContext storyDao.isStoryFavourite(item.id)
        }
        return result
    }

    suspend fun getListStoryItem(): Flow<Item> {

        return if (!isNetworkConnected()) {
            withContext(Dispatchers.IO) {
                storyDao.selectAllStory().asFlow()
            }
        } else {
            return flow {
                withContext(Dispatchers.IO) {
                    val topStories = getTopStoriesRemote()
                    topStories.forEach {
                        val item = getStoryItem(it)
                        if (storyDao.isStoryFavourite(item.id)) {
                            item.isFavourite = true
                        }

                        storyDao.insertStory(item)
                        withContext(Dispatchers.Main.immediate) {
                            emit(item)
                        }
                    }
                }
            }
        }


    }
}