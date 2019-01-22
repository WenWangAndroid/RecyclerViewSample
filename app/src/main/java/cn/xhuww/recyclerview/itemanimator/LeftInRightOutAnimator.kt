package cn.xhuww.recyclerview.itemanimator

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v7.widget.RecyclerView

class LeftInRightOutAnimator : BaseItemAnimator() {
    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.animate(holder.itemView)
            .rotationY(-90F)
            .setDuration(removeDuration)
            .start()
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.animate(holder.itemView)
            .rotationY(0F)
            .setDuration(addDuration)
            .start()
    }
}