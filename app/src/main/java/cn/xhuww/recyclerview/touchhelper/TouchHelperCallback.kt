package cn.xhuww.recyclerview.touchhelper

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class TouchHelperCallback : ItemTouchHelper.Callback() {

    //应该返回一个复合标志，用于定义每个状态下的启用移动方向（空闲，滑动，拖动）。
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //当ItemTouchHelper想要将拖动的项目从其旧位置移动到新位置时调用。
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 刷过ViewHolder时调用
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 长按拖拽
    override fun isLongPressDragEnabled(): Boolean = true

    // 滑动操作
    override fun isItemViewSwipeEnabled(): Boolean = false

    // 滑动或拖动ViewHolder时调用。
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
    }
}