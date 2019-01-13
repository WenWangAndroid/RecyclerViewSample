package cn.xhuww.recyclerview.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class SuspendItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    var dividerDrawable: Drawable? = null
    //分割线的展示 position 与 文本内容
    var dividerData: Map<Int, String>? = null

    private val bounds = Rect()

    init {
        val tapeArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        dividerDrawable = tapeArray.getDrawable(0)
        tapeArray.recycle()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val divider = dividerDrawable ?: return
        if (dividerData.isNullOrEmpty()) return

        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            //按照数据给定位置绘制
            if (dividerData!![position] != null) {
                parent.getDecoratedBoundsWithMargins(child, bounds)
                val top = bounds.top
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
        canvas.restore()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val height = dividerDrawable?.intrinsicHeight ?: 0
        //如果数据不为空，则根据dividerDrawable的高度 设置Item间的间距
        if (!dividerData.isNullOrEmpty()) {
            val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            if (dividerData!![position] != null) {
                outRect.set(0, height, 0, 0)
            }
        }
    }
}