package cn.xhuww.recyclerview.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import cn.xhuww.recyclerview.AppResource
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.TextAdapter
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        initView()
    }

    private fun initView() {
        val textAdapter = TextAdapter().apply { items = AppResource.tags }
        val flexBoxLayoutManager = FlexboxLayoutManager(this)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.shape_divider_line_8dp))
        recyclerView.isNestedScrollingEnabled = false

        recyclerView.apply {
            layoutManager = flexBoxLayoutManager
            adapter = textAdapter
            addItemDecoration(itemDecoration)
        }
    }
}