package cn.xhuww.recyclerview.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.View
import cn.xhuww.recyclerview.utils.sp2px

class SuspendItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    var dividerDrawable: Drawable? = null       //普通Item间的分割线
    var groupDividerDrawable: Drawable? = null  //每组之间的分割线
    var contacts: List<Contact>? = null         //联系人数据

    private val bounds = Rect()
    private val textPaint: TextPaint
    @ColorRes
    var textColor: Int = android.R.color.white
        set(value) {
            field = value
            textPaint.color = context.resources.getColor(value)
        }
    var textSize: Float = 20F
        set(value) {
            field = value
            textPaint.textSize = context.sp2px(value)
        }

    var textTypeface = Typeface.DEFAULT_BOLD!!
        set(value) {
            field = value
            textPaint.typeface = value
        }

    init {
        //系统默认的分割线
        val tapeArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        dividerDrawable = tapeArray.getDrawable(0)
        tapeArray.recycle()

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

        textPaint.color = context.resources.getColor(textColor)
        textPaint.textSize = context.sp2px(textSize)
        textPaint.typeface = textTypeface
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val list = contacts ?: return

        c.save()

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val position = params.viewLayoutPosition

            val currentInitial = list[position].initial
            val lastInitial = if (position >= 1) {
                list[position - 1].initial
            } else {
                null
            }

            var isDrawText = false
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val drawable = if (currentInitial == lastInitial) {
                dividerDrawable
            } else {
                isDrawText = true
                groupDividerDrawable
            }

            //绘制分割线
            val top = bounds.top
            val bottom = top + (drawable?.intrinsicHeight ?: 0)
            drawable?.setBounds(left, top, right, bottom)
            drawable?.draw(c)
            //绘制文本内容
            if (isDrawText) {
                drawable ?: return
                textPaint.getTextBounds(currentInitial, 0, currentInitial.length, bounds)
                val textX = child.paddingLeft.toFloat()
                val textY = (child.top - (drawable.intrinsicHeight - bounds.height()) / 2).toFloat()
                c.drawText(currentInitial, textX, textY, textPaint)
            }
        }
        c.restore()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val list = contacts ?: return
        val drawable = groupDividerDrawable ?: return

        //只判断layoutManager为LinearLayoutManager的情况，其他情况的不做处理
        val layoutManager = parent.layoutManager as? LinearLayoutManager ?: return

        val position = layoutManager.findFirstVisibleItemPosition()
        if (position < 0 || position > list.size - 1) {
            return
        }

        val child = parent.findViewHolderForLayoutPosition(position)!!.itemView

        val currentInitial = list[position].initial
        val nextInitial = if (position + 1 < list.size) {
            list[position + 1].initial
        } else {
            null
        }

        parent.getDecoratedBoundsWithMargins(child, bounds)

        c.save()
        if (currentInitial != nextInitial) {
            //顶部移出效果
            if (child.top + child.height < drawable.intrinsicHeight) {
                c.translate(0f, (child.height + child.top - drawable.intrinsicHeight).toFloat())
            }
        }

        val left = parent.paddingLeft
        val top = parent.paddingTop
        val right = parent.right - parent.paddingRight
        val bottom = parent.paddingTop + drawable.intrinsicHeight

        drawable.setBounds(left, top, right, bottom)
        drawable.draw(c)

        textPaint.getTextBounds(currentInitial, 0, currentInitial.length, bounds)
        val textX = child.paddingLeft.toFloat()
        val textY =
            (parent.paddingTop + drawable.intrinsicHeight - (drawable.intrinsicHeight - bounds.height()) / 2).toFloat()
        c.drawText(currentInitial, textX, textY, textPaint)
        c.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, 0)
        val list = contacts ?: return

        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

        val currentInitial = list[position].initial
        val lastInitial = if (position >= 1) {
            list[position - 1].initial
        } else {
            null
        }

        //当前首字母与上一个首字母相同 则属于同一组，使用 dividerDrawable
        val drawable = if (currentInitial == lastInitial) {
            dividerDrawable
        } else {
            groupDividerDrawable
        }

        val height = drawable?.intrinsicHeight ?: 0
        //设置Item的上下左右偏移
        outRect.set(0, height, 0, 0)
    }
}