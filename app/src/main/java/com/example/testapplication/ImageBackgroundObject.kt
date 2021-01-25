package com.example.testapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.view.MotionEvent

/**
 * @author kienht
 * @company OICSoft
 * @since 06/01/2020
 */
//class ImageBackgroundObject(
//    var bitmap: Bitmap? = null
//) : RoomPlanObject {
//
//    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
//        .apply {
//            isDither = true
//        }
//
//    override fun draw(canvas: Canvas) {
//        if (bitmap != null && !bitmap!!.isRecycled) {
//            canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
//        }
//    }
//
//    override fun destroy() {
//        if (bitmap?.isRecycled == false) {
//            bitmap?.recycle()
//        }
//    }
//
//    override fun onTouchEvent(
//        touchX: Float,
//        touchY: Float,
//        matrix: Matrix,
//        event: MotionEvent
//    ): Boolean {
//        return false
//    }
//}