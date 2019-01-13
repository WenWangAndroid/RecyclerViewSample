package cn.xhuww.recyclerview.layoutmanager

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.xhuww.recyclerview.R
import kotlinx.android.synthetic.main.recycle_item_image_text_vertical.view.*

class ImageTextAdapter : RecyclerView.Adapter<ImageTextAdapter.ViewHolder>() {
    var items: List<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var createViewCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_item_image_text_vertical, parent, false)
        Log.i("TAG", "-------------createViewCount：${++createViewCount}")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(content: String) {
            itemView.imageView.setImageResource(R.mipmap.image)
            itemView.textView.text = content
        }
    }
}
