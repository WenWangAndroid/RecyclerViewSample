package cn.xhuww.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.xhuww.recyclerview.itemanimator.ItemAnimatorActivity
import cn.xhuww.recyclerview.itemdecoration.ItemDecorationActivity
import cn.xhuww.recyclerview.layoutmanager.LayoutManagerActivity
import cn.xhuww.recyclerview.snaphelper.SnapHelperActivity
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
            startActivity(LayoutManagerActivity::class.java)
        }

        btnItemTouchHelper.setOnClickListener {
            startActivity(ItemTouchHelperActivity::class.java)
        }

        btnSnapHelper.setOnClickListener {
            startActivity(SnapHelperActivity::class.java)
        }

        btnItemDecoration.setOnClickListener {
            startActivity(ItemDecorationActivity::class.java)
        }

        btnItemAnimator.setOnClickListener {
            startActivity(ItemAnimatorActivity::class.java)
        }
    }
}
