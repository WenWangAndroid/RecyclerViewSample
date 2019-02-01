package cn.xhuww.recyclerview.itemanimator;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;

public class LeftInRightOutAnimator extends BaseItemAnimator {

    @Override
    void prepareAnimateAddImpl(RecyclerView.ViewHolder viewHolder) {
        super.prepareAnimateAddImpl(viewHolder);
        View view = viewHolder.itemView;
        view.setTranslationX(-view.getWidth());
    }

    @Override
    void animateAddImpl(RecyclerView.ViewHolder viewHolder, ViewPropertyAnimator animation) {
        animation.setDuration(getAddDuration()).alpha(1F).translationX(0F);
    }

    @Override
    void animateRemoveImpl(RecyclerView.ViewHolder viewHolder, ViewPropertyAnimator animation) {
        animation.setDuration(getRemoveDuration()).translationX(-viewHolder.itemView.getWidth());
    }
}
