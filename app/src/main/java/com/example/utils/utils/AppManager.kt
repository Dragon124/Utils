package com.example.utils.utils

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

class AppManager {
    companion object {
        var isForeground = false // 应用是否在前台

        //监听APP状态
        fun init(application : Application) {
            application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity : Activity, savedInstanceState : Bundle?) {
                }

                override fun onActivityStarted(activity : Activity) {
                }

                override fun onActivityResumed(activity : Activity) {
                    isForeground = true
                }

                override fun onActivityPaused(activity : Activity) {}
                override fun onActivityStopped(activity : Activity) {
                    isForeground = false
                }

                override fun onActivitySaveInstanceState(activity : Activity, outState : Bundle) {}
                override fun onActivityDestroyed(activity : Activity) {
                }
            })
        }
    }
}