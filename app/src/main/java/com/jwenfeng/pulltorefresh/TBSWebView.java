package com.jwenfeng.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.tencent.smtt.sdk.WebView;


/**
 * 当前类注释:
 * 作者：jinwenfeng on 2017/6/13 21:07
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2017 jinwenfeng
 * © 版权所有，未经允许不得传播
 */

public class TBSWebView extends WebView {

    public TBSWebView(Context context) {
        super(context);
    }

    public TBSWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TBSWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        final int offset = getWebScrollY();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }



}
