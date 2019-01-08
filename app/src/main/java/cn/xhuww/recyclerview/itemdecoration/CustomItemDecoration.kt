package cn.xhuww.recyclerview.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class CustomItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    var dividerDrawable: Drawable? = null

    var orientation = Orientation.VERTICAL
    private val bounds = Rect()

    init {
        val tapeArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        dividerDrawable = tapeArray.getDrawable(0)
        tapeArray.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        when (orientation) {
            Orientation.VERTICAL -> drawVertical(c, parent)
            Orientation.HORIZONTAL -> drawHorizontal(c, parent)
            Orientation.GRID -> {
                drawVertical(c, parent)
                drawHorizontal(c, parent)
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val divider = dividerDrawable ?: return
        val layoutManager = parent.layoutManager ?: return

        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + Math.round(child.translationY)
            val top = bottom - divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val divider = dividerDrawable ?: return
        val layoutManager = parent.layoutManager ?: return

        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            layoutManager.getDecoratedBoundsWithMargins(child, bounds)

            val right = bounds.right + Math.round(child.translationX)
            val left = right - divider.intrinsicWidth
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }


    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val height = dividerDrawable?.intrinsicWidth ?: 0
        val width = dividerDrawable?.intrinsicWidth ?: 0

        when (orientation) {
            Orientation.VERTICAL -> outRect.set(0, 0, 0, height)
            Orientation.HORIZONTAL -> outRect.set(0, 0, width, 0)
            Orientation.GRID -> outRect.set(0, 0, width, height)
        }
    }

    enum class Orientation {
        VERTICAL, HORIZONTAL, GRID
    }
}