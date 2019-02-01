package cn.xhuww.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnItemDecoration).setOnClickListener(this);
        findViewById(R.id.btnLayoutManager).setOnClickListener(this);
        findViewById(R.id.btnSnapHelper).setOnClickListener(this);
        findViewById(R.id.btnItemTouchHelper).setOnClickListener(this);
        findViewById(R.id.btnItemAnimator).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String type = null;
        switch (id) {
            case R.id.btnLayoutManager:
                type = AppConstant.layout_manager;
                break;
            case R.id.btnItemDecoration:
                type = AppConstant.item_decoration;
                break;
            case R.id.btnSnapHelper:
                type = AppConstant.snap_helper;
                break;
            case R.id.btnItemTouchHelper:
                type = AppConstant.item_touch_helper;
                break;
            case R.id.btnItemAnimator:
                type = AppConstant.item_animator;
                break;
        }
        if (type != null) {
            startActivity(RecyclerViewActivity.intent(this, type));
        }
    }
}
