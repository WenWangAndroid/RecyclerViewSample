package cn.xhuww.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.xhuww.recyclerview.layoutmanager.RecyclerViewActivity
import cn.xhuww.recyclerview.touchhelper.ItemTouchHelperActivity
import cn.xhuww.recyclerview.utils.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        btnLayoutManager.setOnClickListener {
            startActivity(RecyclerViewActivity::class.java)
        }

        btnItemTouchHelper.setOnClickListener {
            startActivity(ItemTouchHelperActivity::class.java)
        }
    }
}
