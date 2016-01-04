package com.example.admin.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.admin.zhbj.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends Activity {


    private ViewPager vpGuide;

    ArrayList<ImageView> mImageViewList;
    private LinearLayout llPointGroup;//引导圆点的父控件
    private int mPointWidth;//原点间的距离
    private View viewRedPoint;
    private Button btnStart;


    private static final int[] ImageIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        setContentView(R.layout.activity_guide);
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);//引导原点父控件
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);
        viewRedPoint = findViewById(R.id.view_red_point);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //更新sp,表示已展示
                PrefUtils.setBoolean(GuideActivity.this,"is_use_guide_page",true);


                //跳转页面
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });



        initView();
        vpGuide.setAdapter(new MyViewPageAdpater());
        vpGuide.addOnPageChangeListener(new GuidepageListener());

    }

    protected void initView() {
        mImageViewList = new ArrayList<ImageView>();

        //初始化引导页的3个页面
        for (int i = 0; i < ImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(ImageIds[i]);
            mImageViewList.add(imageView);
        }
        // 初始化页面的小圆点
        for (int i = 0; i < ImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shap_point_gray);//设置引导页默认原点

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i > 0) {
                params.leftMargin = 20;//设置点间隔
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);//将原点添加给线性布局

            //获取视图树
            llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                //当layout结束时会执行此方法
                @Override
                public void onGlobalLayout() {
                    System.out.println("layout 结束");
                    llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    //基础三部曲
                    //measure(测量布局大小) layout(界面的位置) omdraw(绘制)
                    mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
                }
            });
        }
    }

    /**
     * viewpage 的适配器
     */
    class MyViewPageAdpater extends PagerAdapter {

        @Override
        public int getCount() {
            return ImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * viewPage 的滑动监听
     */
    class GuidepageListener implements ViewPager.OnPageChangeListener {
        //滑动的事件
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //当前位置：position，百分比：positionOffset，移动距离：positionOffsetPixels

            //小圆点移动的宽度
            int len = (int) (mPointWidth * positionOffset + mPointWidth * position);
            //获取当前红点点的布局参数
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();

            params.leftMargin = len;
            //再次设置小红点的布局参数
            viewRedPoint.setLayoutParams(params);


        }

        //某个页面被选中
        @Override
        public void onPageSelected(int position) {
            if (position == ImageIds.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        //滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
