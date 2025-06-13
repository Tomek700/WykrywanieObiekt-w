package com.example.wykrywanieobiektow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.label.ImageLabel



class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var detectedObjects: List<DetectedObject> = emptyList()
    private var labels: List<ImageLabel> = emptyList()

    fun setObjects(objects: List<DetectedObject>) {
        detectedObjects = objects
        invalidate()
    }

    fun setLabels(newLabels: List<ImageLabel>) {
        labels = newLabels
        invalidate()
    }
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Rysowanie obiektów
        for (obj in detectedObjects) {
            val box = obj.boundingBox
            canvas.drawRect(box, boxPaint)
            val label = if (obj.labels.isNotEmpty()) obj.labels[0].text else "Unknown"
            canvas.drawText(label, box.left.toFloat(), box.top.toFloat() - 10, textPaint)
        }

        // Rysowanie etykiet
        var y = 100f
        for (label in labels) {
            val labelText = "${label.text} (${(label.confidence * 100).toInt()}%)"
            canvas.drawText(labelText, 50f, y, textPaint)
            y += 60f
        }
    }
}