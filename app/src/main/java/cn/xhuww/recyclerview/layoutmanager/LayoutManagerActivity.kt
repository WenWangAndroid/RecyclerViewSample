package cn.xhuww.recyclerview.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.GridView
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.ImageTextAdapter
import kotlinx.android.synthetic.main.activity_recycle_view.*

class LayoutManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)
        val dataList = ArrayList<String>()
        for (i in 0 until 10) {
            dataList.add(resources.getString(R.string.item_position, i))
        }

        val imageTextAdapter = ImageTextAdapter().apply {
            items = dataList
        }

//        val linearLayoutManager = LinearLayoutManager(this)
//        val linearLayoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
//        val linearLayoutManager =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

//        val gridLayoutManager = GridLayoutManager(this, 3)
//        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, true)
        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = imageTextAdapter
        }
    }
}