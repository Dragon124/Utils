package com.example.utils.activity

import android.app.Application
import android.util.Log
import com.example.utils.utils.AppManager

class App : Application() {


    override fun onCreate() {
        instance = this
        super.onCreate()
        AppManager.init(this)
    }

    companion object {
        private var instance: App? = null
        fun instance() = instance
    }

    fun get(){
        Log.e("123","11")
    }
}