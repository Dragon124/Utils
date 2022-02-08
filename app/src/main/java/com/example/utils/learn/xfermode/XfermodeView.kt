package com.example.utils.learn.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import com.example.utils.R
import com.example.utils.utils.DensityUtils
import com.example.utils.utils.ImageUtils

class XfermodeView(context : Context, attrs : AttributeSet?) : View(context, attrs) {
    private var bitmapOne : Bitmap = ImageUtils.getResBitmap(resources, R.mipmap.image_one)
    private var bitmapTwo : Bitmap = ImageUtils.getResBitmap(resources, R.mipmap.image_two)
    private val paint : Paint by lazy {
        Paint()
    }
    private val xfermode : PorterDuffXfermode by lazy {
        PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
    }

    init {
        var newBitmapOne = Bitmap.createBitmap(bitmapOne.width + DensityUtils.dp2px(10), bitmapOne.height + DensityUtils.dp2px(10), Bitmap.Config.ARGB_8888)
        var d = Canvas(newBitmapOne)
        d.drawColor(context.resources.getColor(R.color.color_transparency))
        var rectF = Rect(0, 0, bitmapOne.width, bitmapOne.height)
        var rectF2 = Rect(0, 0, bitmapOne.width + DensityUtils.dp2px(10), bitmapOne.height + DensityUtils.dp2px(10))
        d.drawBitmap(bitmapOne, rectF2, rectF, paint)
        bitmapOne = newBitmapOne

        var newBitmapTwo = Bitmap.createBitmap(bitmapTwo.width + DensityUtils.dp2px(10), bitmapTwo.height + DensityUtils.dp2px(10), Bitmap.Config.ARGB_8888)
        var canvas = Canvas(newBitmapTwo)
        canvas.drawColor(context.resources.getColor(R.color.color_transparency))
        var rect = Rect(DensityUtils.dp2px(20), DensityUtils.dp2px(20), bitmapOne.height + DensityUtils.dp2px(20), bitmapOne.width + DensityUtils.dp2px(20))
        var rect2 = Rect(0, 0, bitmapOne.width + DensityUtils.dp2px(10), bitmapOne.height + DensityUtils.dp2px(10))
        canvas.drawBitmap(bitmapTwo, rect2, rect, paint)
        bitmapTwo = newBitmapTwo
    }

    override fun onDraw(canvas : Canvas) {
//        var layer = canvas.saveLayer((width - bitmapOne.width) / 2f, (height - bitmapOne.height) / 2f, (height - bitmapOne.height) / 2f + bitmapOne.width, (height - bitmapOne.height) / 2f + bitmapOne.width, paint)
//        canvas.drawBitmap(bitmapOne, (width - bitmapOne.width) / 2f, (height - bitmapOne.height) / 2f, paint)
        paint.color = context.resources.getColor(R.color.colorAccent)
        canvas.drawRect((width - bitmapOne.width) / 2f, (height - bitmapOne.height) / 2f, (height - bitmapOne.height) / 2f + bitmapOne.width / 2, (height - bitmapOne.height) / 2f + bitmapOne.width / 2, paint)

//        paint.xfermode = xfermode
        paint.setColor(context.resources.getColor(R.color.colorPrimary))
        canvas.drawRect((width - bitmapOne.width) / 2f + bitmapOne.width / 2, (height - bitmapOne.height) / 2f + bitmapOne.height / 2, (width - bitmapOne.width) / 2f + bitmapOne.width, (height - bitmapOne.height) / 2f + bitmapOne.height, paint)
//        canvas.drawBitmap(bitmapTwo, (width - bitmapTwo.width) / 2f, (height - bitmapTwo.height) / 2f, paint)
//        paint.xfermode = null
//        canvas.restoreToCount(layer)
    }
}