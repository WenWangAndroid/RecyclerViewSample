package cn.xhuww.recyclerview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import cn.xhuww.recyclerview.R;

public class TestDialog extends Dialog {
    private ImageView imageView;

    public TestDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_test, null);
        setContentView(rootView);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0, 0, 0, 0);

        //减去状态栏高度
        int screenHeight = getScreenHeight();
        int statusBarHeight = getStatusBarHeight();
        int dialogHeight = screenHeight - statusBarHeight;

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = dialogHeight;

        window.setAttributes(lp);
    }

    //获取屏幕高度
    private int getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    //获取状态栏高度
    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        Resources res = getContext().getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    @Override
    public void show() {
        super.show();
        View bgView = findViewById(R.id.dialog_bg);
        imageView = findViewById(R.id.image);
        imageView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_in));
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_out);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.setAnimation(animation);
        super.dismiss();
    }
}
