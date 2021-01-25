package com.example.testapplication

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_test)
        val view = findViewById<CustomView>(R.id.custom_view)
        button.setOnClickListener {
            val bitmap = view.captureRoomBitMap()
            getScreenshotPath()
                .takeUnless { it.isEmpty() }
                ?.let { bitmap.saveToFile(it, true) }
                ?.absolutePath
        }
    }
}

fun Bitmap.saveToFile(path: String, isRecycle: Boolean = false): File? {
    return try {
        val file = File(path)
        file.createNewFile()
        FileOutputStream(file).use { out -> this.compress(Bitmap.CompressFormat.PNG, 100, out) }
        file
    } catch (e: Exception) {
        null
    } finally {
        if (isRecycle && !isRecycled) {
            recycle()
        }
    }
}

fun Context.getScreenshotPath(): String {
    val folderPath = "${cacheDir}/screenshot"
    val folder = File(folderPath)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    return "${folderPath}/${System.currentTimeMillis()}.png"
}