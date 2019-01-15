package cn.xhuww.recyclerview.itemdecoration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.ImageAdapter
import kotlinx.android.synthetic.main.activity_recycle_view.*

class ItemDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        val linearLayoutManager = LinearLayoutManager(this)
        val imageAdapter = ImageAdapter().apply {
            items = (0..20).map { R.mipmap.image }
        }

        val map = HashMap<Int, String>()
        //每隔2个位置绘制分割线
        //添加高度为8dp的分割线
        val itemDecoration = SuspendItemDecoration(this).apply {
            divider = resources.getDrawable(R.drawable.shape_divider_line_vertical_8dp)
            dividerData = (0..20).map { "第${it / 2}组" }
        }

        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = imageAdapter
            addItemDecoration(itemDecoration)
        }
    }
}


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.item_decoration_handle, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        recyclerView.removeItemDecoration(itemDecoration)
//
//        when (item.itemId) {
//            android.R.id.home -> onBackPressed()
//
//            R.id.no_decoration -> recyclerView.removeItemDecoration(itemDecoration)
//
//            R.id.horizontal_decoration -> {
//                (itemDecoration as DividerItemDecoration).apply {
//                    setDrawable(resources.getDrawable(R.drawable.shape_divider_horizontal_line_8dp))
//                    setOrientation(DividerItemDecoration.HORIZONTAL)
//                }
//                recyclerView.replaceItemDecoration(itemDecoration)
//            }
//
//            R.id.vertical_decoration -> {
//                (itemDecoration as DividerItemDecoration).apply {
//                    setDrawable(resources.getDrawable(R.drawable.shape_divider_line_vertical_8dp))
//                    setOrientation(DividerItemDecoration.VERTICAL)
//                }
//                recyclerView.replaceItemDecoration(itemDecoration)
//            }
//
//            R.id.custom_decoration -> {
//                (itemDecoration as DividerItemDecoration).setDrawable(
//                    resources.getDrawable(R.drawable.shape_divider_line_vertical_8dp)
//                )
//                recyclerView.replaceItemDecoration(itemDecoration)
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//        return true
//    }

//    private fun RecyclerView.replaceItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
//        removeItemDecoration(itemDecoration)
//        addItemDecoration(itemDecoration)
//        adapter?.notifyDataSetChanged()
//    }
//}



