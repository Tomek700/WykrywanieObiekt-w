package com.example.wykrywanieobiektow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.objects.DetectedObject

class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val boxPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }

    private val textPaint = Paint().apply {
        color = Color.GREEN
        textSize = 54f
        typeface = Typeface.DEFAULT_BOLD
    }

    private var detectedObjects: List<DetectedObject> = emptyList()

    fun setObjects(objects: List<DetectedObject>) {
        detectedObjects = objects
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (obj in detectedObjects) {
            val box = obj.boundingBox
            canvas.drawRect(box, boxPaint)
            val label = if (obj.labels.isNotEmpty()) obj.labels[0].text else "Unknown"
            canvas.drawText(label, box.left.toFloat(), box.top.toFloat() - 10, textPaint)
        }
    }
}