package com.catastrophic.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codex.hackernews.base.BaseViewModel
import com.codex.hackernews.data.model.Item
import com.codex.hackernews.data.repository.StoryRepository
import com.codex.hackernews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onErrorCollect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    app: Application
) : BaseViewModel(app) {


    private val _storyItems = MutableLiveData<Resource<List<Item>>>()
    val storyitems:MutableLiveData<Resource<List<Item>>>
        get() = _storyItems

    suspend fun getStories (): Flow<Item> {
      return storyRepository.getListStoryItem()
    }

}