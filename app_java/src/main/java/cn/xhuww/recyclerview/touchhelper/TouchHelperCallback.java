package cn.xhuww.recyclerview.touchhelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

public class TouchHelperCallback extends ItemTouchHelper.Callback {

    //应该返回一个复合标志，用于定义每个状态下的启用移动方向（空闲，滑动，拖动）。
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags; //拖拽标志
        int swipeFlags; //滑动标志
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager ||
                layoutManager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //左滑
            swipeFlags = ItemTouchHelper.LEFT;
        }
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags);
    }

    //当ItemTouchHelper想要将拖动的项目从其旧位置移动到新位置时调用。
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return true;
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    // 刷过ViewHolder时调用
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    // 长按拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    // 滑动操作
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    // 滑动或拖动ViewHolder时调用。
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    // 完成交互以及相关动画时调用
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }
}
