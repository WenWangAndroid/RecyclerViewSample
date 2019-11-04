package cn.xhuww.recyclerview.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.annotation.Px
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.View

class TimeLineItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    private val linePaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val circlePaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    @Px
    var timeLineWidth: Int = 0

    @Px
    var lineWidth = 0

    @Px
    var circleWidth = 0

    @ColorRes
    var lineColor: Int = android.R.color.holo_blue_light
        set(value) {
            field = value
            linePaint.color = context.resources.getColor(value)
        }

    @ColorRes
    var circleColor: Int = android.R.color.holo_blue_light
        set(value) {
            field = value
            linePaint.color = context.resources.getColor(value)
        }

    init {
        linePaint.color = context.resources.getColor(lineColor)
        circlePaint.color = context.resources.getColor(circleColor)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        c.save()

        val lineRadius = lineWidth / 2
        val circleRadius = circleWidth / 2

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val position = params.viewLayoutPosition

            val centerX = child.left - timeLineWidth / 2
            val centerY = child.top + child.height / 2

            var lineTop: Int = child.top
            var lineBottom: Int = child.bottom

            when (position) {
                0 -> lineTop = centerY
                state.itemCount - 1 -> lineBottom = centerY
            }

            val lineRect = Rect(centerX - lineRadius, lineTop, centerX + lineRadius, lineBottom)

            c.drawRect(lineRect, linePaint)
            c.drawCircle(centerX.toFloat(), centerY.toFloat(), circleRadius.toFloat(), circlePaint)
        }
        c.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(timeLineWidth, 0, 0, 0)
    }
}