package com.app.maptranslation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.app.maptranslation.composable.MyApp
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
//        applicationContext
//
//        Locale.getDefault().country
//        val locale = Locale.getDefault().country
//        Log.i("아현", "$locale")

        //setContentView(MainActivity().MyView(this))
    }

    var NameOfFolder = "/TEST"

    inner class MyView(context: Context?) : View(context),
        View.OnClickListener {
        override fun onDraw(canvas: Canvas) {

            // TODO Auto-generated method stub
            super.onDraw(canvas)
            val width = width.toFloat()
            val height = height.toFloat()
            val radius: Float
            radius = if (width > height) {
                height / 4
            } else {
                width / 4
            }
            val path = Path()
            path.addCircle(
                width / 2,
                height / 2, radius,
                Path.Direction.CW
            )
            val paint = Paint()
            paint.color = Color.WHITE
            paint.strokeWidth = 5f
            paint.style = Paint.Style.STROKE
            paint.textSize = 37f
            canvas.drawTextOnPath(
                "  Hi this is a smiley emoji \"\uD83D\uDE01\uD83D\uDE01",
                path, 0f, 0f,
                paint
            )
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            canvas.drawTextOnPath(
                "  Hi this is a smiley emoji \"\uD83D\uDE01\uD83D\uDE01",
                path, 0f, 0f,
                paint
            )
        }

        override fun onClick(view: View) {
            isDrawingCacheEnabled = true
            measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            layout(0, 0, 500, 500)
            buildDrawingCache(true)
            val b = Bitmap.createBitmap(drawingCache)
            isDrawingCacheEnabled = false
            val out: FileOutputStream
            val file = filename
            try {
                out = FileOutputStream(file)
                b.compress(Bitmap.CompressFormat.PNG, 100, out)
                val contentUri = Uri.fromFile(file)
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                mediaScanIntent.data = contentUri
                sendBroadcast(mediaScanIntent)
                out.flush()
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        init {
            setOnClickListener(this)
            // TODO Auto-generated constructor stub
        }
    }

    private val filename: File
        private get() {
            val file_path = Environment.getExternalStorageDirectory().absolutePath + NameOfFolder
            val dir = File(file_path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val ext = ".png"
            return File(dir, "TEST" + System.currentTimeMillis() + ext)
        }

    fun drawEmoji() {

    }
}

