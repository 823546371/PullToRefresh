package com.jwenfeng.pulltorefresh.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.view.FooterView;
import com.jwenfeng.pulltorefresh.R;

/**
 * 当前类注释:
 * 作者：jinwenfeng on 2017/7/19 09:49
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2017 jinwenfeng
 * © 版权所有，未经允许不得传播
 */

public class NormalFooterView extends FrameLayout implements FooterView {

    private TextView tv;

    public NormalFooterView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public NormalFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NormalFooterView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_normal_footer,null);
        addView(view);
        tv = (TextView) view.findViewById(R.id.normal_footer_text);
    }

    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {
        if (progress >= all-10){
            tv.setText("松开加载更多");
        }else{
            tv.setText("上拉加载");
        }
    }

    @Override
    public void finishing(float progress, float all) {

    }

    @Override
    public void loading() {
        tv.setText("加载中...");
    }

    @Override
    public void normal() {
        tv.setText("上拉加载");
    }

    @Override
    public View getView() {
        return this;
    }
}
