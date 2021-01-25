package com.example.testapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.File

class CustomView constructor(
        context: Context,
        attrs: AttributeSet
) : View(context, attrs) {

    private var myPositionBitmap: Bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_my_position, BitmapFactory.Options()
            .apply {
                inScaled = false
            }
    )

    private val selectedLocation: SelectLocationObject
    init {
        selectedLocation =
                SelectLocationObject(myPositionBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
//            imageBackground.draw(this)
            selectedLocation.draw(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            selectedLocation.showAt(event.x, event.y)
            invalidate()
        }
        return true
    }

    fun captureRoomBitMap(): Bitmap {
        val frameBitmap = Bitmap.createBitmap(
            measuredWidth,
            measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        Canvas(frameBitmap).apply /*.withTranslation(myPositionBitmap.width*1.0f/2, myPositionBitmap.height*1.0f/2)*/ {
            this@CustomView.draw(this)
        }
        return frameBitmap
    }
}

