package cn.xhuww.recyclerview.itemanimator

import android.support.v7.widget.RecyclerView
import android.view.ViewPropertyAnimator

class LeftInRightOutAnimator : CommonItemAnimator() {

    override fun prepareAnimateAddImpl(viewHolder: RecyclerView.ViewHolder) {
        val view = viewHolder.itemView
        view.translationX = -view.width.toFloat()
    }

    override fun animateAddImpl(
        viewHolder: RecyclerView.ViewHolder, animation: ViewPropertyAnimator
    ) {
        animation.alpha(1F).translationX(0F)
    }

    override fun animateRemoveImpl(
        viewHolder: RecyclerView.ViewHolder, animation: ViewPropertyAnimator
    ) {
        animation.translationX(-viewHolder.itemView.width.toFloat())
    }
}
