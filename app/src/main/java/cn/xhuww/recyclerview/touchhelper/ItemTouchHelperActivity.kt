package cn.xhuww.recyclerview.touchhelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import cn.xhuww.recyclerview.adapter.ImageAdapter
import cn.xhuww.recyclerview.R
import kotlinx.android.synthetic.main.activity_recycle_view.*

class ItemTouchHelperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        initRecyclerView()
    }

    private fun initRecyclerView() {


        val imageAdapter = ImageAdapter().apply {
            items = arrayListOf(
                R.mipmap.image_page_1,
                R.mipmap.image_page_2,
                R.mipmap.image_page_3,
                R.mipmap.image_page_4,
                R.mipmap.image_page_1,
                R.mipmap.image_page_2,
                R.mipmap.image_page_3,
                R.mipmap.image_page_4,
                R.mipmap.image_page_1
            )
        }

        val callback = TouchHelperCallback()
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)

        val gridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.apply {
            adapter = imageAdapter
            layoutManager = linearLayoutManager
        }
    }
}