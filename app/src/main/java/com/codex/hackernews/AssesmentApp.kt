package com.codex.hackernews

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AssesmentApp: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AssesmentApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun application(): AssesmentApp {
            return instance!!
        }
    }
}