package cn.xhuww.recyclerview.layoutmanager

import android.support.v7.widget.RecyclerView
import android.view.View

class LoopLayoutManager : RecyclerView.LayoutManager() {

    // LayoutParams为RecyclerView的子项创建默认对象。
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT
        )

    // 绘制适配器中所有相关的子视图
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        if (state.itemCount == 0 && state.isPreLayout) {
            return
        }
        detachAndScrapAttachedViews(recycler)

        var actualHeight = 0

        for (i in 0 until itemCount) {
            val scrap = recycler.getViewForPosition(i)
            addView(scrap)
            measureChildWithMargins(scrap, 0, 0)
            val width = getDecoratedMeasuredWidth(scrap)
            val height = getDecoratedMeasuredHeight(scrap)
            layoutDecorated(scrap, 0, actualHeight, width, actualHeight + height)
            actualHeight += height

            //超出界面的就不画了,也不add了
            if (actualHeight > getHeight()) {
                break
            }
        }
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(
        dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State
    ): Int {
        //填充
        fill(dy, recycler, state)
        //滚动
        offsetChildrenVertical(dy * -1)
        //回收已经离开界面的
        recycleOut(dy, recycler, state)

        return dy
    }

    private fun fill(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (dy > 0) {
            val lastView = getChildAt(childCount - 1) ?: return

            val lastPos = getPosition(lastView)
            if (lastView.bottom - dy < height) {
                val scrap: View = if (lastPos == itemCount - 1) {
                    recycler.getViewForPosition(0)
                } else {
                    recycler.getViewForPosition(lastPos + 1)
                }

                addView(scrap)
                measureChildWithMargins(scrap, 0, 0)
                val width = getDecoratedMeasuredWidth(scrap)
                val height = getDecoratedMeasuredHeight(scrap)
                layoutDecorated(scrap, 0, lastView.bottom, width, lastView.bottom + height)
            }
        } else {
            val firstView = getChildAt(0) ?: return
            val layoutPosition = getPosition(firstView)

            if (firstView.top >= 0) {
                val scrap = if (layoutPosition == 0) {
                    recycler.getViewForPosition(itemCount - 1)
                } else {
                    recycler.getViewForPosition(layoutPosition - 1)
                }

                addView(scrap, 0)
                measureChildWithMargins(scrap, 0, 0)
                val width = getDecoratedMeasuredWidth(scrap)
                val height = getDecoratedMeasuredHeight(scrap)
                layoutDecorated(scrap, 0, firstView.top - height, width, firstView.top)
            }
        }
    }

    private fun recycleOut(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        for (i in 0 until itemCount) {
            val view = getChildAt(i) ?: return
            if (dy > 0) {
                if (view.bottom - dy < 0) {
                    removeAndRecycleView(view, recycler)
                }
            } else {
                if (view.top - dy > height) {
                    removeAndRecycleView(view, recycler)
                }
            }
        }
    }
}
