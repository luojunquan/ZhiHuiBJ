package com.news.zhihuibj.fragment;

import android.view.View;

import com.news.zhihuibj.R;


public class ContentFragment extends BaseFragment {
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content,null);
        return view;
    }
}
