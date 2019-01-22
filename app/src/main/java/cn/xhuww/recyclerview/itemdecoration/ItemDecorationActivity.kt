package cn.xhuww.recyclerview.itemdecoration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.ContactAdapter
import kotlinx.android.synthetic.main.activity_recycle_view.*

class ItemDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        val list = (325..454).map {
            val ascii = it / 5
            Contact(ascii.toChar().toString(), "${ascii.toChar()} $it")
        }

        val itemDecoration = SuspendItemDecoration(this).apply {
            dividerDrawable = resources.getDrawable(R.drawable.shape_divider_line_vertical_red_2dp)
            groupDividerDrawable = resources.getDrawable(R.drawable.shape_divider_line_vertical_red_45dp)
            contacts = list
        }

        val linearLayoutManager = LinearLayoutManager(this)
        val contactAdapter = ContactAdapter().apply { items = list }

        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = contactAdapter
            addItemDecoration(itemDecoration)
        }
    }
}


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.item_animator, menu)
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
//                    setDrawable(resources.getDrawable(R.drawable.shape_divider_line_vertical_red_45dp))
//                    setOrientation(DividerItemDecoration.VERTICAL)
//                }
//                recyclerView.replaceItemDecoration(itemDecoration)
//            }
//
//            R.id.custom_decoration -> {
//                (itemDecoration as DividerItemDecoration).setDrawable(
//                    resources.getDrawable(R.drawable.shape_divider_line_vertical_red_45dp)
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



