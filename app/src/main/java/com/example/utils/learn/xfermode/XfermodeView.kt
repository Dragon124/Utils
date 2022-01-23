package com.example.utils.learn.xfermode

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.utils.R
import com.example.utils.utils.ImageUtils

class XfermodeView(context : Context?, attrs : AttributeSet?) : View(context, attrs) {
    private val bitmapOne : Bitmap by lazy {
        ImageUtils.getResBitmap(resources, R.mipmap.image_one)
    }
    private val bitmapTwo : Bitmap by lazy {
        ImageUtils.getResBitmap(resources, R.mipmap.image_two)
    }
    private val paint : Paint by lazy {
        Paint()
    }

    override fun onDraw(canvas : Canvas) {
        canvas.drawBitmap(bitmapOne, (width - bitmapOne.width) / 2f, (height - bitmapOne.height) / 2f, paint)
    }
}