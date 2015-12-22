package com.example.admin.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.admin.zhbj.utils.PrefUtils;

public class SplashActivity extends Activity {

    RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No Titlebar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        startAnim();

    }

    /**
     * 开启动画
     */
    private void startAnim() {
        //动画集合
        AnimationSet set = new AnimationSet(false);


        //旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);//动画时间
        rotate.setFillAfter(true);//保持动画状态
        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);//动画时间
        scale.setFillAfter(true);//保持动画状态

        //渐变动画
        AlphaAnimation alp = new AlphaAnimation(0, 1);
        alp.setDuration(1000);//动画时间
        alp.setFillAfter(true);//保持动画状态

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alp);

        //设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
            //    SharedPreferences sp = SharedPreferences();

               jumpNextPage();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rlRoot.startAnimation(set);
    }

    /**
     * 跳转到下一个页面
     */
    private  void  jumpNextPage(){
        //判断之前有没有显示过新手引导界面
        boolean userGuide = PrefUtils.getBoolean(this,"is_use_guide_page",false);


        // 跳转到新手引导页
        if(userGuide){
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));

        }else{
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

    }
        finish();
    }
}
