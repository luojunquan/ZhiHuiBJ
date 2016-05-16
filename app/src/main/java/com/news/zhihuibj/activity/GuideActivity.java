package com.news.zhihuibj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.news.zhihuibj.R;
import com.news.zhihuibj.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends Activity {
    private static final int[] mImageIds = new int[] { R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3 };

    private ViewPager vpGuide;
    private ArrayList<ImageView> mImageViewList;

    private LinearLayout llPointGroup;// ����Բ��ĸ��ؼ�

    private int mPointWidth;// Բ���ľ���

    private View viewRedPoint;// С���

    private Button btnStart;// ��ʼ����

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ������
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = findViewById(R.id.view_red_point);
        btnStart = (Button) findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // ����sp, ��ʾ�Ѿ�չʾ����������
                PrefUtils.setBoolean(GuideActivity.this,
                        "is_user_guide_showed", true);

                // ��ת��ҳ��
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        initViews();
        vpGuide.setAdapter(new GuideAdapter());

        vpGuide.setOnPageChangeListener(new GuidePageListener());
    }

    /**
     * ��ʼ������
     */
    private void initViews() {
        mImageViewList = new ArrayList<ImageView>();

        // ��ʼ������ҳ��3��ҳ��
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImageIds[i]);// ��������ҳ����
            mImageViewList.add(image);
        }

        // ��ʼ������ҳ��СԲ��
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);// ��������ҳĬ��Բ��

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    10, 10);
            if (i > 0) {
                params.leftMargin = 10;// ����Բ����
            }

            point.setLayoutParams(params);// ����Բ��Ĵ�С

            llPointGroup.addView(point);// ��Բ����Ӹ����Բ���
        }

        // ��ȡ��ͼ��, ��layout�����¼����м���
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // ��layoutִ�н�����ص��˷���
                    @Override
                    public void onGlobalLayout() {
                        System.out.println("layout ����");
                        llPointGroup.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                - llPointGroup.getChildAt(0).getLeft();
                        System.out.println("Բ�����:" + mPointWidth);
                    }
                });
    }

    /**
     * ViewPager����������
     *
     * @author Kevin
     *
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
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
     * viewpager�Ļ�������
     *
     * @author Kevin
     *
     */
    class GuidePageListener implements ViewPager.OnPageChangeListener {

        // �����¼�
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // System.out.println("��ǰλ��:" + position + ";�ٷֱ�:" + positionOffset
            // + ";�ƶ�����:" + positionOffsetPixels);
            int len = (int) (mPointWidth * positionOffset) + position
                    * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint
                    .getLayoutParams();// ��ȡ��ǰ���Ĳ��ֲ���
            params.leftMargin = len;// ������߾�

            viewRedPoint.setLayoutParams(params);// ���¸�С������ò��ֲ���
        }

        // ĳ��ҳ�汻ѡ��
        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {// ���һ��ҳ��
                btnStart.setVisibility(View.VISIBLE);// ��ʾ��ʼ����İ�ť
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        // ����״̬�����仯
        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

}
