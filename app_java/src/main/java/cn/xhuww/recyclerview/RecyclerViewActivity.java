package cn.xhuww.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import cn.xhuww.recyclerview.itemanimator.ItemAnimatorFragment;

public class RecyclerViewActivity extends AppCompatActivity {
    private static final String KEY_TYPE = "KEY_TYPE";

    public static Intent intent(Activity activity, String type) {
        Intent intent = new Intent(activity, RecyclerViewActivity.class);
        intent.putExtra(KEY_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        String type = getIntent().getStringExtra(KEY_TYPE);
        if (!TextUtils.isEmpty(type)) {
            setTitle(type);
            Fragment fragment = null;
            switch (type) {
                case AppConstant.item_decoration:
                    break;
                case AppConstant.layout_manager:
                    break;
                case AppConstant.snap_helper:
                    break;
                case AppConstant.item_touch_helper:
                    break;
                case AppConstant.item_animator:
                    fragment = new ItemAnimatorFragment();
                    break;
            }
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_container, fragment)
                        .commit();
            }
        }
    }
}
