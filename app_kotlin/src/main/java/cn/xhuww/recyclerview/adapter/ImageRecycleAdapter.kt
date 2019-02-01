package cn.xhuww.recyclerview.adapter

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.xhuww.recyclerview.R
import kotlinx.android.synthetic.main.recycle_item_image.view.*
import kotlinx.android.synthetic.main.recycle_item_text_image.view.*

class ImageRecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //需要展示的图片列表数据
    var items: List<Any> = ArrayList()
        set(value) {
            field = value
            //数据已改变，通知RecyclerView刷新
            notifyDataSetChanged()
        }

    // 创建一个承载子项视图的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_IMAGE ->
                ViewHolder(inflater.inflate(R.layout.recycle_item_image, parent, false))
            else ->
                TextImageViewHolder(inflater.inflate(R.layout.recycle_item_text_image, parent, false))
        }
    }

    //返回列表子项长度
    override fun getItemCount(): Int = items.size

    //将指定位置的数据与视图绑定
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val data = items[position]

        when (viewHolder) {
            is ViewHolder ->
                if (data is Int) viewHolder.bindView(data)

            is TextImageViewHolder ->
                if (data is String) viewHolder.bindView(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = items[position]
        //如果数据是 Int 则只展示图片，如果是String 则展示 文本+图片
        return when (data) {
            is Int -> VIEW_TYPE_IMAGE
            else -> VIEW_TYPE_TEXT_IMAGE
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //将数据与View绑定
        fun bindView(@DrawableRes imageResource: Int) {
            itemView.imageView.setImageResource(imageResource)
        }
    }

    class TextImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(titleStr: String) {
            itemView.titleTextView.text = titleStr
        }
    }

    companion object {
        const val VIEW_TYPE_IMAGE = 1
        const val VIEW_TYPE_TEXT_IMAGE = 2
    }
}