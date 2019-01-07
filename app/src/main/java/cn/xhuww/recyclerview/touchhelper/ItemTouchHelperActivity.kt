package cn.xhuww.recyclerview.touchhelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.xhuww.recyclerview.adapter.ImageAdapter
import cn.xhuww.recyclerview.AppResource
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.layoutmanager.HorizontalLayoutManager
import kotlinx.android.synthetic.main.activity_recycle_view.*

class ItemTouchHelperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        initView()
    }

    private fun initView() {
        val imageAdapter = ImageAdapter().apply {
            items = AppResource.urls
        }
        val linearLayoutManager = HorizontalLayoutManager()

        recyclerView.apply {
            adapter = imageAdapter
            layoutManager = linearLayoutManager
        }
    }
}