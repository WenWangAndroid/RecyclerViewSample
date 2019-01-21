package cn.xhuww.recyclerview.snaphelper

import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView


class ViewPagerSnapHelper : PagerSnapHelper() {

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {

        val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)

        return if (position >= layoutManager.itemCount) {
            0
        } else {
            super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        }
    }
}