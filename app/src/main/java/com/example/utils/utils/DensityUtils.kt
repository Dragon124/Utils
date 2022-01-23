package com.example.utils.utils

import android.content.res.Resources
import android.util.TypedValue

class DensityUtils {
    companion object {

        fun dp2px(dip : Int) : Int {
            val scale : Float = Resources.getSystem().displayMetrics.density
            return (dip * scale + 0.5f).toInt()
        }

        fun px2dp(px : Int) : Int {
            val scale : Float = Resources.getSystem().displayMetrics.density
            return (px / scale + 0.5f).toInt()
        }

        fun px2sp(spValue : Float) : Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().displayMetrics).toInt()
        }

        fun sp2px(spValue : Float) : Int {
            var scale = Resources.getSystem().displayMetrics.scaledDensity;
            return (spValue * scale + 0.5f).toInt()
        }
    }
}