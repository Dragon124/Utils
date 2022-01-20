package com.example.utils.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.annotation.ColorInt
import kotlin.math.cos
import kotlin.math.sin

class WaterMarkUtils {
    companion object {
        //画水印
        fun onDrawWatermark(context : Context, canvas : Canvas, text : String, startY : Int, radian : Int, @ColorInt waterMarkColor : Int) {
            //左右边距
            val lrPadding = Resources.getSystem().displayMetrics.density * 20
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            //白色半透明
            paint.color = waterMarkColor
            paint.textSize = 22 * context.resources.displayMetrics.density
            paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            paint.isDither = true
            paint.isFilterBitmap = true
            val rectText = Rect()
            //得到text占用宽高， 单位：像素
            paint.getTextBounds(text, 0, text.length, rectText)
            //水印倾斜后的高度
            val sinHeight = (sin(Math.toRadians(radian.toDouble())) * rectText.width()).toFloat()
            //需要添加多少行水印
            val y = ((canvas.height - startY) / sinHeight).toInt() + 1
            canvas.save()
            canvas.translate(lrPadding, 0f)
            //顺时针转45度
            canvas.rotate(radian.toFloat())
            val sinRadian = sin(Math.toRadians(radian.toDouble())).toFloat()
            val cosRadian = cos(Math.toRadians(radian.toDouble())).toFloat()
            var i = 0
            while (i < y) {
                //使用的Y轴就是斜边
                var useY = startY + sinHeight * i + lrPadding
                //已知旋的角度和 斜边=startY + sinHeight * i  可求X=sin*angle Y=cos*angle
                canvas.drawText(text, sinRadian * useY, cosRadian * useY, paint)
                i += 2
            }
            canvas.restore()
            canvas.save()
            //左移准备画第二列水印
            canvas.translate(canvas.width - rectText.width() - lrPadding, 0f)
            canvas.rotate(radian.toFloat())
            i = 1
            while (i < y) {
                //使用的Y轴就是斜边
                var useY = startY + sinHeight * i + lrPadding
                //已知旋的角度和 斜边=startY + sinHeight * i  可求X=sin*angle Y=cos*angle
                canvas.drawText(text, sinRadian * useY, cosRadian * useY, paint)
                i += 2
            }
            canvas.restore()
        }
    }
}