package cn.xhuww.recyclerview.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.View
import cn.xhuww.recyclerview.utils.sp2px

class SuspendItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    var divider: Drawable? = null
    //分割线的展示 position 与 文本内容
    var dividerData: List<String>? = null
    val bounds = Rect()

    private val textPaint: TextPaint
    @ColorRes
    var textColor: Int = android.R.color.white
        set(value) {
            field = value
            textPaint.color = context.resources.getColor(value)
        }
    var textSize: Float = 20F
        set(value) {
            field = value
            textPaint.textSize = context.sp2px(value)
        }

    var textTypeface = Typeface.DEFAULT_BOLD!!
        set(value) {
            field = value
            textPaint.typeface = value
        }

    init {
        val tapeArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = tapeArray.getDrawable(0)
        tapeArray.recycle()

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

        textPaint.color = context.resources.getColor(textColor)
        textPaint.textSize = context.sp2px(textSize)
        textPaint.typeface = textTypeface
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        canvas.save()

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val position = params.viewLayoutPosition

            if (!shouldDrawDecoration(position)) {
                continue
            }

            val content = dividerData!![position]

            //绘制分割线
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val dividerTop = bounds.top
            val dividerBottom = dividerTop + divider!!.intrinsicHeight
            divider!!.setBounds(left, dividerTop, right, dividerBottom)
            divider!!.draw(canvas)

            //绘制分割线上的文本
            textPaint.getTextBounds(content, 0, content.length, bounds)
            val textX = child.paddingLeft.toFloat()
            val textY = (child.top - (divider!!.intrinsicHeight - bounds.height()) / 2).toFloat()
            canvas.drawText(content, textX, textY, textPaint)
        }
        canvas.restore()
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val divider = divider ?: return
        val list = dividerData ?: return

        val position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (position < 0 || position > list.size - 1) {
            return
        }

        canvas.save()

        val child = parent.findViewHolderForLayoutPosition(position)!!.itemView

        val currentContent = list[position]
        if (position + 1 < list.size) {
            val nextContent = list[position + 1]
            if (child.top + child.height < divider.intrinsicHeight && currentContent != nextContent) {
                canvas.translate(0f, (child.height + child.top - divider.intrinsicHeight).toFloat())
            }
        }

        val left = parent.paddingLeft
        val top = parent.paddingTop
        val right = parent.right - parent.paddingRight
        val bottom = parent.paddingTop + divider.intrinsicHeight

        divider.setBounds(left, top, right, bottom)
        divider.draw(canvas)

        //绘制分割线上的文本
        textPaint.getTextBounds(currentContent, 0, currentContent.length, bounds)
        val textX = child.paddingLeft.toFloat()
        val textY =
            (parent.paddingTop + divider.intrinsicHeight - (divider.intrinsicHeight - bounds.height()) / 2).toFloat()
        canvas.drawText(currentContent, textX, textY, textPaint)

        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

        if (shouldDrawDecoration(position)) {
            outRect.set(0, divider!!.intrinsicHeight, 0, 0)
        }
    }

    private fun shouldDrawDecoration(position: Int): Boolean {
        divider ?: return false

        val list = dividerData ?: return false

        if (position < 0 || position > list.size - 1) {
            return false
        }

        val content = list[position]
        if (content.isEmpty()) {
            return false
        }

        if (position == 0) {
            return true
        }

        return (position > 0 && content != list[position - 1])
    }
}