package cn.xhuww.recyclerview.itemanimator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.v7.widget.SimpleItemAnimator
import java.util.ArrayList

abstract class BaseItemAnimator : SimpleItemAnimator() {

    private var sDefaultInterpolator: TimeInterpolator? = null

    private val mPendingRemovals = ArrayList<ViewHolder>()
    private val mPendingAdditions = ArrayList<ViewHolder>()
    private val mPendingMoves = ArrayList<MoveInfo>()
    private val mPendingChanges = ArrayList<ChangeInfo>()

    private var mAdditionsList = ArrayList<ArrayList<ViewHolder>>()
    private var mMovesList = ArrayList<ArrayList<MoveInfo>>()
    private var mChangesList = ArrayList<ArrayList<ChangeInfo>>()

    private var mAddAnimations = ArrayList<ViewHolder>()
    private var mMoveAnimations = ArrayList<ViewHolder>()
    private var mRemoveAnimations = ArrayList<ViewHolder>()
    private var mChangeAnimations = ArrayList<ViewHolder>()

    //回调方法，RecyclerView添加Item时调用
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        resetAnimation(holder)
        holder.itemView.alpha = 0f
        mPendingAdditions.add(holder)
        return true
    }

    //Item 移动时调用
    override fun animateMove(
        holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int
    ): Boolean {
        val view = holder.itemView
        val realFromX = fromX + view.translationX.toInt()
        val realFromY = fromY + view.translationY.toInt()

        resetAnimation(holder)

        val deltaX = toX - fromX
        val deltaY = toY - fromY
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder)
            return false
        }
        if (deltaX != 0) {
            view.translationX = (-deltaX).toFloat()
        }
        if (deltaY != 0) {
            view.translationY = (-deltaY).toFloat()
        }

        mPendingMoves.add(MoveInfo(holder, realFromX, realFromY, toX, toY))
        return true
    }

    //Item 改变时调用
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder?,
        fromX: Int, fromY: Int, toX: Int, toY: Int
    ): Boolean {
        if (oldHolder === newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY)
        }

        val prevTranslationX = oldHolder.itemView.translationX
        val prevTranslationY = oldHolder.itemView.translationY
        val prevAlpha = oldHolder.itemView.alpha
        resetAnimation(oldHolder)

        val deltaX = (toX.toFloat() - fromX.toFloat() - prevTranslationX).toInt()
        val deltaY = (toY.toFloat() - fromY.toFloat() - prevTranslationY).toInt()

        oldHolder.itemView.translationX = prevTranslationX
        oldHolder.itemView.translationY = prevTranslationY
        oldHolder.itemView.alpha = prevAlpha

        if (newHolder != null) {
            resetAnimation(newHolder)
            newHolder.itemView.translationX = (-deltaX).toFloat()
            newHolder.itemView.translationY = (-deltaY).toFloat()
            newHolder.itemView.alpha = 0f
        }

        mPendingChanges.add(ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY))
        return true
    }

    //Item 移除时回调
    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        resetAnimation(holder)
        mPendingRemovals.add(holder)
        return true
    }

    //结束某个Item的动画
    override fun endAnimation(item: RecyclerView.ViewHolder) {
        val view = item.itemView
        view.animate().cancel()

        for (i in mPendingMoves.indices.reversed()) {
            val moveInfo = mPendingMoves[i]
            if (moveInfo.holder === item) {
                view.translationY = 0f
                view.translationX = 0f
                dispatchMoveFinished(item)
                mPendingMoves.removeAt(i)
            }
        }

        endChangeAnimation(mPendingChanges, item)
        if (mPendingRemovals.remove(item)) {
            view.alpha = 1f
            dispatchRemoveFinished(item)
        }
        if (mPendingAdditions.remove(item)) {
            view.alpha = 1f
            dispatchAddFinished(item)
        }

        for (i in mChangesList.indices.reversed()) {
            val changes = mChangesList[i]
            endChangeAnimation(changes, item)
            if (changes.isEmpty()) {
                mChangesList.removeAt(i)
            }
        }

        for (i in mMovesList.indices.reversed()) {
            val moves = mMovesList[i]
            for (j in moves.indices.reversed()) {
                val moveInfo = moves[j]
                if (moveInfo.holder === item) {
                    view.translationY = 0f
                    view.translationX = 0f
                    dispatchMoveFinished(item)
                    moves.removeAt(j)
                    if (moves.isEmpty()) {
                        mMovesList.removeAt(i)
                    }
                    break
                }
            }
        }

        for (i in mAdditionsList.indices.reversed()) {
            val additions = mAdditionsList[i]
            if (additions.remove(item)) {
                view.alpha = 1f
                dispatchAddFinished(item)
                if (additions.isEmpty()) {
                    mAdditionsList.removeAt(i)
                }
            }
        }

        dispatchFinishedWhenDone()
    }

    //结束所有动画
    override fun endAnimations() {
        var count = mPendingMoves.size
        for (i in count - 1 downTo 0) {
            val item = mPendingMoves[i]
            val view = item.holder.itemView
            view.translationY = 0f
            view.translationX = 0f
            dispatchMoveFinished(item.holder)
            mPendingMoves.removeAt(i)
        }
        count = mPendingRemovals.size
        for (i in count - 1 downTo 0) {
            val item = mPendingRemovals[i]
            dispatchRemoveFinished(item)
            mPendingRemovals.removeAt(i)
        }
        count = mPendingAdditions.size
        for (i in count - 1 downTo 0) {
            val item = mPendingAdditions[i]
            item.itemView.alpha = 1f
            dispatchAddFinished(item)
            mPendingAdditions.removeAt(i)
        }
        count = mPendingChanges.size
        for (i in count - 1 downTo 0) {
            endChangeAnimationIfNecessary(mPendingChanges[i])
        }
        mPendingChanges.clear()
        if (!isRunning) {
            return
        }

        var listCount = mMovesList.size
        for (i in listCount - 1 downTo 0) {
            val moves = mMovesList[i]
            count = moves.size
            for (j in count - 1 downTo 0) {
                val moveInfo = moves[j]
                val item = moveInfo.holder
                val view = item.itemView
                view.translationY = 0f
                view.translationX = 0f
                dispatchMoveFinished(moveInfo.holder)
                moves.removeAt(j)
                if (moves.isEmpty()) {
                    mMovesList.remove(moves)
                }
            }
        }

        listCount = mAdditionsList.size
        for (i in listCount - 1 downTo 0) {
            val additions = mAdditionsList[i]
            count = additions.size
            for (j in count - 1 downTo 0) {
                val item = additions[j]
                val view = item.itemView
                view.alpha = 1f
                dispatchAddFinished(item)
                additions.removeAt(j)
                if (additions.isEmpty()) {
                    mAdditionsList.remove(additions)
                }
            }
        }

        listCount = mChangesList.size
        for (i in listCount - 1 downTo 0) {
            val changes = mChangesList[i]
            count = changes.size
            for (j in count - 1 downTo 0) {
                endChangeAnimationIfNecessary(changes[j])
                if (changes.isEmpty()) {
                    mChangesList.remove(changes)
                }
            }
        }

        cancelAll(mRemoveAnimations)
        cancelAll(mMoveAnimations)
        cancelAll(mAddAnimations)
        cancelAll(mChangeAnimations)

        dispatchAnimationsFinished()
    }

    //是否正在运行动画
    override fun isRunning(): Boolean = (!mPendingAdditions.isEmpty()
            || !mPendingChanges.isEmpty()
            || !mPendingMoves.isEmpty()
            || !mPendingRemovals.isEmpty()
            || !mMoveAnimations.isEmpty()
            || !mRemoveAnimations.isEmpty()
            || !mAddAnimations.isEmpty()
            || !mChangeAnimations.isEmpty()
            || !mMovesList.isEmpty()
            || !mAdditionsList.isEmpty()
            || !mChangesList.isEmpty())

    //运行动画
    override fun runPendingAnimations() {
        val removalsPending = !mPendingRemovals.isEmpty()
        val movesPending = !mPendingMoves.isEmpty()
        val changesPending = !mPendingChanges.isEmpty()
        val additionsPending = !mPendingAdditions.isEmpty()
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
            return
        }

        for (holder in mPendingRemovals) {
            animateRemoveImpl(holder)
        }

        mPendingRemovals.clear()

        if (movesPending) {
            val moves = ArrayList<MoveInfo>()
            moves.addAll(mPendingMoves)
            mMovesList.add(moves)
            mPendingMoves.clear()
            val mover = Runnable {
                for (moveInfo in moves) {
                    animateMoveImpl(
                        moveInfo.holder, moveInfo.fromX, moveInfo.fromY,
                        moveInfo.toX, moveInfo.toY
                    )
                }
                moves.clear()
                mMovesList.remove(moves)
            }
            if (removalsPending) {
                val view = moves[0].holder.itemView
                ViewCompat.postOnAnimationDelayed(view, mover, removeDuration)
            } else {
                mover.run()
            }
        }

        if (changesPending) {
            val changes = ArrayList<ChangeInfo>()
            changes.addAll(mPendingChanges)
            mChangesList.add(changes)
            mPendingChanges.clear()
            val changer = Runnable {
                for (change in changes) {
                    animateChangeImpl(change)
                }
                changes.clear()
                mChangesList.remove(changes)
            }
            if (removalsPending) {
                val holder = changes[0].oldHolder ?: return
                ViewCompat.postOnAnimationDelayed(holder.itemView, changer, removeDuration)
            } else {
                changer.run()
            }
        }

        if (additionsPending) {
            val additions = ArrayList<ViewHolder>()
            additions.addAll(mPendingAdditions)
            mAdditionsList.add(additions)
            mPendingAdditions.clear()
            val adder = Runnable {
                for (holder in additions) {
                    animateAddImpl(holder)
                }
                additions.clear()
                mAdditionsList.remove(additions)
            }

            if (removalsPending || movesPending || changesPending) {
                val removeDuration = if (removalsPending) removeDuration else 0
                val moveDuration = if (movesPending) moveDuration else 0
                val changeDuration = if (changesPending) changeDuration else 0
                val totalDelay = removeDuration + Math.max(moveDuration, changeDuration)
                val view = additions[0].itemView
                ViewCompat.postOnAnimationDelayed(view, adder, totalDelay)
            } else {
                adder.run()
            }
        }
    }

    private fun resetAnimation(holder: ViewHolder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = ValueAnimator().interpolator
        }
        holder.itemView.animate().interpolator = sDefaultInterpolator
        endAnimation(holder)
    }

    private fun endChangeAnimation(infoList: MutableList<ChangeInfo>, item: ViewHolder) {
        for (i in infoList.indices.reversed()) {
            val changeInfo = infoList[i]
            if (endChangeAnimationIfNecessary(changeInfo, item)) {
                if (changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                    infoList.remove(changeInfo)
                }
            }
        }
    }

    private fun endChangeAnimationIfNecessary(changeInfo: ChangeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder)
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder)
        }
    }

    private fun endChangeAnimationIfNecessary(changeInfo: ChangeInfo, item: ViewHolder?): Boolean {
        var oldItem = false

        item ?: return false

        when (item) {
            changeInfo.newHolder -> changeInfo.newHolder = null
            changeInfo.oldHolder -> {
                changeInfo.oldHolder = null
                oldItem = true
            }
            else -> return false
        }

        item.itemView.alpha = 1f
        item.itemView.translationX = 0f
        item.itemView.translationY = 0f
        dispatchChangeFinished(item, oldItem)
        return true
    }

    private fun cancelAll(viewHolders: List<ViewHolder>) {
        for (i in viewHolders.indices.reversed()) {
            viewHolders[i].itemView.animate().cancel()
        }
    }

    private fun animateMoveImpl(
        holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int
    ) {
        val view = holder.itemView
        val deltaX = toX - fromX
        val deltaY = toY - fromY

        val animation = view.animate()

        if (deltaX != 0) {
            animation.translationX(0f)
        }

        if (deltaY != 0) {
            animation.translationY(0f)
        }

        mMoveAnimations.add(holder)

        animation.setDuration(moveDuration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animator: Animator) {
                dispatchMoveStarting(holder)
            }

            override fun onAnimationCancel(animator: Animator) {
                if (deltaX != 0) {
                    view.translationX = 0f
                }
                if (deltaY != 0) {
                    view.translationY = 0f
                }
            }

            override fun onAnimationEnd(animator: Animator) {
                animation.setListener(null)
                dispatchMoveFinished(holder)
                mMoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    private fun animateChangeImpl(changeInfo: ChangeInfo) {
        val oldHolder = changeInfo.oldHolder ?: return
        val oldView = oldHolder.itemView
        val oldViewAnim = oldView.animate().setDuration(
            changeDuration
        )

        mChangeAnimations.add(oldHolder)
        oldViewAnim.translationX((changeInfo.toX - changeInfo.fromX).toFloat())
        oldViewAnim.translationY((changeInfo.toY - changeInfo.fromY).toFloat())
        oldViewAnim.alpha(0f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animator: Animator) {
                dispatchChangeStarting(oldHolder, true)
            }

            override fun onAnimationEnd(animator: Animator) {
                oldViewAnim.setListener(null)
                oldView.alpha = 1f
                oldView.translationX = 0f
                oldView.translationY = 0f
                dispatchChangeFinished(oldHolder, true)
                mChangeAnimations.remove(oldHolder)
                dispatchFinishedWhenDone()
            }
        }).start()

        val newHolder = changeInfo.newHolder ?: return
        val newView = newHolder.itemView

        val newViewAnimation = newView.animate()
        mChangeAnimations.add(newHolder)
        newViewAnimation.translationX(0f).translationY(0f).setDuration(changeDuration)
            .alpha(1f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animator: Animator) {
                    dispatchChangeStarting(newHolder, false)
                }

                override fun onAnimationEnd(animator: Animator) {
                    newViewAnimation.setListener(null)
                    newView.alpha = 1f
                    newView.translationX = 0f
                    newView.translationY = 0f
                    dispatchChangeFinished(newHolder, false)
                    mChangeAnimations.remove(newHolder)
                    dispatchFinishedWhenDone()
                }
            }).start()
    }

    abstract fun animateRemoveImpl(holder: ViewHolder)
    abstract fun animateAddImpl(holder: ViewHolder)

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    inner class MoveInfo(
        var holder: ViewHolder,
        var fromX: Int,
        var fromY: Int,
        var toX: Int,
        var toY: Int
    )

    inner class ChangeInfo(
        var oldHolder: ViewHolder?,
        var newHolder: ViewHolder?,
        var fromX: Int,
        var fromY: Int,
        var toX: Int,
        var toY: Int
    )
}
