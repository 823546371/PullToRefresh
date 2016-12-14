package com.jwenfeng.library.pulltorefresh.view;

import android.view.View;

/**
 * 当前类注释:
 * 作者：jinwenfeng on 2016/12/6 13:48
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * © 版权所有，未经允许不得传播
 */
public interface FooterView {

    /**
     * 开始下拉
     */
    void begin();

    /**
     * 回调的精度,单位为px
     *
     * @param progress 当前高度
     * @param all      总高度   为默认高度的2倍
     */
    void progress(float progress, float all);

    void finishing(float progress, float all);
    /**
     * 下拉完毕
     */
    void loading();

    /**
     * 看不见的状态
     */
    void normal();

    /**
     * 返回当前视图
     * */
    View getView();

}
