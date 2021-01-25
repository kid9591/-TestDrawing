package com.example.testapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val positionPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            isDither = true
            isFilterBitmap = true
        }

    private val positionBitmap: Bitmap

    private val density: Int
        get() = when (context.resources.displayMetrics.density) {
            0.75f -> DisplayMetrics.DENSITY_LOW
            1f -> DisplayMetrics.DENSITY_MEDIUM
            1.5f -> DisplayMetrics.DENSITY_HIGH
            2f -> DisplayMetrics.DENSITY_XHIGH
            3f -> DisplayMetrics.DENSITY_XXHIGH
            4f -> DisplayMetrics.DENSITY_XXXHIGH
            else -> DisplayMetrics.DENSITY_DEFAULT
        }

    private var positionX = -1f
    private var positionY = -1f

    init {
        val source = ContextCompat.getDrawable(context, R.drawable.ic_my_position)
        val bitmap = if (source is BitmapDrawable) {
            source.bitmap
        } else {
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_my_position,
                BitmapFactory.Options()
                    .apply {
                        inScaled = false
                    }
            )
        }

        positionBitmap = Bitmap.createScaledBitmap(
            bitmap,
            bitmap.width,
            bitmap.height,
            true
        )

        doOnPreDraw {
            drawPosition(
                (width / 2 - positionBitmap.width / 2).toFloat(),
                (height / 2 - positionBitmap.height / 2).toFloat()
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (positionX >= 0 && positionY >= 0) {
            canvas.drawBitmap(
                positionBitmap,
                positionX,
                positionY,
                positionPaint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            drawPosition(event.x, event.y)
        }
        return true
    }

    @WorkerThread
    fun capture(): Bitmap? = try {
        Bitmap.createBitmap(
            measuredWidth,
            measuredHeight,
            Bitmap.Config.ARGB_8888
        ).also {
            Canvas(it)
                .apply {
                    density = this@CustomView.density
                    drawColor(Color.GREEN)
                }.let { canvas ->
                    draw(canvas)
                }
        }
    } catch (e: Exception) {
        Log.e(TAG, "capture: ", e)
        null
    }

    private fun drawPosition(x: Float, y: Float) {
        positionX = x
        positionY = y
        invalidate()
    }

    companion object {
        private val TAG: String = CustomView::class.java.simpleName
    }
}

