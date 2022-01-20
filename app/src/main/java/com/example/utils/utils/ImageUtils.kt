package com.example.utils.utils

import android.graphics.*
import android.view.View

class ImageUtils {
    companion object {
        /**
         * @param view View
         * @param startViewY Int 截图viewY的起点
         * @param watermarkStartY Int 水印Y轴的起点
         * @param watermarkText String 水印文字
         * @param radian Int 水印倾斜角度
         * @param watermarkColor Int  水印的颜色
         * @return Bitmap
         */
        fun getViewBitmap(view : View, startViewY : Int = 0, watermarkStartY : Int = 0, watermarkText : String = "", radian : Int = 45, watermarkColor : Int = 0) : Bitmap {
            //RGB_565更节省资源，但是没有透明度
            var bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight - startViewY, Bitmap.Config.RGB_565)
            var canvas = Canvas(bitmap)
            canvas.translate(0f, -startViewY.toFloat())
            if (view.background != null) {
                view.background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            view.draw(canvas)
            //水印文字为空跳过水印绘制
            if (watermarkText.isNotEmpty()) {
                WaterMarkUtils.onDrawWatermark(view.context, canvas, watermarkText, watermarkStartY, radian, watermarkColor)
            }
            return bitmap
        }
    }
}