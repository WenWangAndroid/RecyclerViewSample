package cn.xhuww.recyclerview.adapter

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.xhuww.recyclerview.R
import kotlinx.android.synthetic.main.recycle_item_image.view.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var items: ArrayList<Int> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addData(@DrawableRes res: Int) {
        items.add(res)
        notifyItemInserted(items.size - 1)
    }

    fun insertData(@DrawableRes res: Int, position: Int) {
        if (outOfSize(position)) return
        items.add(position, res)
        notifyItemInserted(position)
    }

    fun removeData(position: Int) {
        if (outOfSize(position)) return
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateData(@DrawableRes res: Int, position: Int) {
        if (outOfSize(position)) return
        items[position] = res
        notifyItemChanged(position)
    }

    private fun outOfSize(position: Int): Boolean = position < 0 || position >= items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showImage(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showImage(@DrawableRes res: Int) {
            itemView.imageView.setImageResource(res)
        }
    }
}

