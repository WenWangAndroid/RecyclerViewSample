package cn.xhuww.recyclerview.itemdecoration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import cn.xhuww.recyclerview.R
import cn.xhuww.recyclerview.adapter.ImageAdapter
import kotlinx.android.synthetic.main.activity_recycle_view.*

class ItemDecorationActivity : AppCompatActivity() {
    private lateinit var itemDecoration: RecyclerView.ItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gridLayoutManager = GridLayoutManager(this, 3)
        val imageAdapter = ImageAdapter().apply {
            items = (0..30).map { R.mipmap.image }
        }
//        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration = CustomItemDecoration(this).apply {
            orientation = CustomItemDecoration.Orientation.GRID
        }

        recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = imageAdapter
            addItemDecoration(itemDecoration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item_decoration_handle, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        recyclerView.removeItemDecoration(itemDecoration)

        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.no_decoration -> recyclerView.removeItemDecoration(itemDecoration)

            R.id.horizontal_decoration -> {
                (itemDecoration as DividerItemDecoration).apply {
                    setDrawable(resources.getDrawable(R.drawable.shape_divider_horizontal_line_8dp))
                    setOrientation(DividerItemDecoration.HORIZONTAL)
                }
                recyclerView.replaceItemDecoration(itemDecoration)
            }

            R.id.vertical_decoration -> {
                (itemDecoration as DividerItemDecoration).apply {
                    setDrawable(resources.getDrawable(R.drawable.shape_divider_line_vertical_8dp))
                    setOrientation(DividerItemDecoration.VERTICAL)
                }
                recyclerView.replaceItemDecoration(itemDecoration)
            }

            R.id.custom_decoration -> {
                (itemDecoration as DividerItemDecoration).setDrawable(
                    resources.getDrawable(R.drawable.shape_divider_line_vertical_8dp)
                )
                recyclerView.replaceItemDecoration(itemDecoration)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun RecyclerView.replaceItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
        removeItemDecoration(itemDecoration)
        addItemDecoration(itemDecoration)
        adapter?.notifyDataSetChanged()
    }
}
