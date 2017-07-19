package com.jwenfeng.pulltorefresh.view;

import android.content.Context;
import android.util.AttributeSet;

import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

/**
 * 当前类注释:
 * 作者：jinwenfeng on 2017/7/19 09:41
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2017 jinwenfeng
 * © 版权所有，未经允许不得传播
 */

public class NormalPullToRefreshLayout extends PullToRefreshLayout {

    public NormalPullToRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public NormalPullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NormalPullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setFooterView(new NormalFooterView(context));
        setHeaderView(new NormalHeadView(context));
    }
}
