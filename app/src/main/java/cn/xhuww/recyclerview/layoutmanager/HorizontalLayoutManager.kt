package cn.xhuww.recyclerview.layoutmanager

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup

class HorizontalLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0 && state.isPreLayout) {
            return
        }

        detachAndScrapAttachedViews(recycler)

        //横向绘制子View,则需要知道 X轴的偏移量
        var offsetX = 0

        /**
         * 绘制并添加view 此处把所有的子布局都绘制出来了，是不应该的
         */
        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)

            layoutDecoratedWithMargins(view, offsetX, 0, offsetX + width, height)

            offsetX += width
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(
        dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State
    ): Int {
        //日志显示，左滑dx值为正数，右滑dx值为负数
        Log.i("TAG", "----------dx：$dx")
        /**
         * 横向移动所有子View
         * 为什么要 * -1 ? 屏幕xy轴原点在左上角，左移则需要View的坐标 x - offset  右移则需要 x + offset
         * 所以需要 dx * -1
         */
        offsetChildrenHorizontal(dx * -1)
        return dx
    }
}
