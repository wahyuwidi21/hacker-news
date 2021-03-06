package com.codex.hackernews.data.source.local

import androidx.room.*
import com.codex.hackernews.data.model.Item

@Dao
interface StoryDao {
    @Query("SELECT * FROM Story")
    fun selectAllStory(): List<Item>

    @Query("SELECT isFavourite FROM Story WHERE id=:id")
    fun isStoryFavourite(id:String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStory(cats: List<Item>?)

    @Update(entity = Item::class)
    fun updateFavourite(item: Item)
}