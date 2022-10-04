package com.app.maptranslation.util

import android.graphics.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitmapDrawHelper {

    fun drawBitmapIcon(emojiText:String) : BitmapDescriptor? {
        return emojiText.takeIf { it.isNotEmpty() }?.let {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.apply {
                style = Paint.Style.FILL
                color = Color.WHITE
                textSize = 100f
                textAlign = Paint.Align.CENTER
            }

            val baseLine = -paint.ascent()
            val width = (paint.measureText(it) + 20f).toInt() // 사각형 너비
            val height = (baseLine + paint.descent() + 20f).toInt()
            val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // 사각형을 그릴 캔버스 너비

            val rect = Rect(0, 0, width, height)
            val rectF = RectF(rect)
            Canvas(image).apply {
                drawRoundRect(rectF, 20f, 20f, paint)
                drawText(it, width / 2f, (height+50) / 2f, paint)
            }
            BitmapDescriptorFactory.fromBitmap(image)
        }
    }
}