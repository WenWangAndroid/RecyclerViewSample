package cn.xhuww.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.xhuww.recyclerview.R
import kotlinx.android.synthetic.main.recycle_item_image_text_vertical.view.*

class ImageTextAdapter : BaseAdapter<String, ImageTextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_item_image_text_vertical, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(content: String, position: Int) {
            if (position % 2 == 0) {
                itemView.imageView.setImageResource(R.mipmap.image_positive)
            } else {
                itemView.imageView.setImageResource(R.mipmap.image)
            }
            itemView.textView.text = content
        }
    }
}

