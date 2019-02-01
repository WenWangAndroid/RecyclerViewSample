package cn.xhuww.recyclerview.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import cn.xhuww.recyclerview.AppResource
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.TextAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

//        val dataList = ArrayList<Any>()
//        for (i in 0 until 10) {
//            if (i % 3 == 0)
//                dataList.add(R.mipmap.image)
//            else
//                dataList.add(resources.getString(R.string.positive_content))
//        }
//
//        val imageAdapter = ImageRecycleAdapter().apply {
//            items = dataList
//        }
//        val linearLayoutManager = LinearLayoutManager(this)
//        //添加分割线
//        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
//        recyclerView.apply {
//            layoutManager = linearLayoutManager
//            adapter = imageAdapter
//            addItemDecoration(itemDecoration)
//        }
        initView()
    }

    private fun initView() {
        val textAdapter = TextAdapter().apply { items = AppResource.tags }
        val flexBoxLayoutManager = FlexboxLayoutManager(this)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.shape_divider_line_vertical_red_45dp))
        recyclerView.isNestedScrollingEnabled = false

        recyclerView.apply {
            layoutManager = flexBoxLayoutManager
            adapter = textAdapter
            addItemDecoration(itemDecoration)
        }
    }
}
