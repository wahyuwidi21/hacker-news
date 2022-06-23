package com.codex.hackernews.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codex.hackernews.base.BaseViewModel
import com.codex.hackernews.data.model.Comment
import com.codex.hackernews.data.model.Item
import com.codex.hackernews.data.repository.CommentRepository
import com.codex.hackernews.data.repository.StoryRepository
import com.codex.hackernews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailStoryViewModel @Inject constructor(
    private val commentRepository: CommentRepository, private val storyRepository: StoryRepository,
    app: Application
) : BaseViewModel(app) {


    private val _comments = MutableLiveData<Resource<List<Comment>>>()
    val comments:MutableLiveData<Resource<List<Comment>>>
        get() = _comments

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite:MutableLiveData<Boolean>
        get() = _isFavourite

    internal fun addToFavourite(item: Item){
        viewModelScope.launch {
            _isFavourite.postValue(storyRepository.setItemFavourite(item,true))
        }
    }

    internal fun removeFromFavourite(item: Item){
        viewModelScope.launch {
            _isFavourite.postValue(storyRepository.setItemFavourite(item, false))
        }
    }

    internal fun getItems(childList:List<Int>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _comments.postValue(Resource.loading(null))
                try {
                    _comments.postValue(Resource.success(commentRepository.getListStoryItem(childList)))
                } catch (e: Exception) {
                    _comments.postValue(Resource.error(e.toString(), null, Throwable(e.message, null)))
                }
            }
        }
    }

}