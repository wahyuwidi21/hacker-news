package com.codex.hackernews.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.codex.hackernews.data.model.Item
import com.google.gson.Gson
import javax.inject.Inject

class PreferencesManager @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("hacker_news", Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()
}