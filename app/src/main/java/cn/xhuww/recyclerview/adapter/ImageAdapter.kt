package cn.xhuww.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.xhuww.recyclerview.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycle_item_image.view.*

class ImageAdapter : BaseAdapter<String, ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showImage(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showImage(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(itemView.imageView)
        }
    }
}

