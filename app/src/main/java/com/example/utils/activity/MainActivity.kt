package com.example.utils.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.utils.R
import com.example.utils.activity.App.Companion.instance
import com.example.utils.utils.AppManager
import com.example.utils.utils.FileUtils
import com.example.utils.utils.ImageUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(v : View) {
        var view = findViewById<View>(R.id.ll)
        FileUtils.saveBitmap(this, ImageUtils.getViewBitmap(view, watermarkText="水印", watermarkColor = ContextCompat.getColor(this, R.color.color_transparency)))
    }
}