package cn.xhuww.recyclerview.snaphelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.ImageAdapter
import cn.xhuww.recyclerview.layoutmanager.HorizontalLayoutManager
import kotlinx.android.synthetic.main.activity_recycle_view.*

class SnapHelperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        val imageAdapter = ImageAdapter().apply {
            items = arrayListOf(
                R.mipmap.image_page_1,
                R.mipmap.image_page_2,
                R.mipmap.image_page_3,
                R.mipmap.image_page_4
            )
        }

        ViewPagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.apply {
            layoutManager = HorizontalLayoutManager()
            adapter = imageAdapter
        }
    }
}
