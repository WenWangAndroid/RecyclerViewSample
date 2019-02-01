package cn.xhuww.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.xhuww.recyclerview.itemdecoration.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    var items: List<Contact> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ContactAdapter.ViewHolder, position: Int) {
        viewHolder.bindView(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bindView(contact: Contact) {
            textView.text = contact.name
        }
    }
}