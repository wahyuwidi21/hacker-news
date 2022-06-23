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

    internal fun getItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _storyItems.postValue(Resource.loading(null))
                try {
                    _storyItems.postValue(Resource.success(storyRepository.getListStoryItem()))
                } catch (e: Exception) {
                    _storyItems.postValue(Resource.error(e.toString(), null, Throwable(e.message, null)))
                }
            }
        }
    }

}