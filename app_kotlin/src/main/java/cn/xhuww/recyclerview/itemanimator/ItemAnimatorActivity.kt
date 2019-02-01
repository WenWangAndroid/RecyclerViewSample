package cn.xhuww.recyclerview.itemanimator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import cn.xhuww.recyclerview.adapter.ImageAdapter
import cn.xhuww.recyclerview.R
import kotlinx.android.synthetic.main.activity_recycle_view.*

class ItemAnimatorActivity : AppCompatActivity() {
    lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用

        initRecyclerView()
    }

    private fun initRecyclerView() {
        imageAdapter = ImageAdapter()

        val gridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
            setDrawable(resources.getDrawable(R.drawable.shape_divider_vertical_line_white_8dp))
        }
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.apply {
            adapter = imageAdapter
            layoutManager = linearLayoutManager
            itemAnimator = LeftInRightOutAnimator()
            addItemDecoration(itemDecoration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_animator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId ?: return super.onOptionsItemSelected(item)

        when (itemId) {
            R.id.home -> onBackPressed()
            R.id.add_item -> imageAdapter.addData(R.mipmap.image_page_1)
            R.id.insert_item -> imageAdapter.insertData(R.mipmap.image_page_2, 1)
            R.id.remove_item -> imageAdapter.removeData(0)
            R.id.update_item -> imageAdapter.updateData(R.mipmap.image_page_4, 2)
        }
        return true
    }
}
