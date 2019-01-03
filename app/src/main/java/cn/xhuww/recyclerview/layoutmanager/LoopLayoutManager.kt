package cn.xhuww.recyclerview.layoutmanager

import android.support.v7.widget.RecyclerView

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
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun scrollVerticallyBy(
        dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State
    ): Int {

        return super.scrollVerticallyBy(dy, recycler, state)
    }

    private fun fill(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (dy > 0) {
            val lastView = getChildAt(childCount - 1) ?: return 0

            var lastPos = getPosition(lastView)
        } else {

        }
        return 0
    }
}
