package com.example.admin.zhbj;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.example.admin.zhbj.fragment.ContentFragment;
import com.example.admin.zhbj.fragment.LeftMenuFragment;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity {

    private static final String FRAGMENT_LEFT_MENU = "fragment_right_menu";
    private static final String FRAGMENT = "fragment_content";
    public  static SlidingMenu menu;//侧滑的对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.fragment_right_menu);

        initFragment();
    }

    /**
     * 初始化Fragment，将Fragment数据填充给布局文件中
     */
    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();//开启事务

        //用fragment替换fragmentlayout
        transaction.replace(R.id.ff_letf_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
        transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT);

        transaction.commit();//提交事务


    }

}
