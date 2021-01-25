package com.example.testapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customView = findViewById<CustomView>(R.id.custom_view)
        val buttonCapture = findViewById<Button>(R.id.button_capture)
        val imageView = findViewById<AppCompatImageView>(R.id.image_view)

        buttonCapture.setOnClickListener {
            lifecycleScope.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    customView.capture()
                }
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                }
            }
        }

        customView.doOnPreDraw {
            buttonCapture.performClick()
        }
    }
}