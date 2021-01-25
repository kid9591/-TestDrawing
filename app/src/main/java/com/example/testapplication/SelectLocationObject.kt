package com.example.testapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

/**
 * @author kienht
 * @company OICSoft
 * @since 06/01/2020
 */
class SelectLocationObject(
    private val markerBitmap: Bitmap
) {

    private val drawingPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            isDither = true
        }

    var x: Float = 0f
    var y: Float = 0f

    fun draw(canvas: Canvas) {
        if (x > 0f && y > 0f && !markerBitmap.isRecycled) {
            canvas.drawBitmap(
                markerBitmap,
                x - markerBitmap.width / 2,
                y - markerBitmap.height, //to show the bottom of the marker (the vertex) at the touch point
                drawingPaint
            )
        }
    }

    fun showAt(newX: Float, newY: Float) {
        if (this.x != newX && this.y != newY) {
            this.x = newX
            this.y = newY
        }
    }

    fun hide() {
        this.x = 0f
        this.y = 0f
    }
}