package com.jwenfeng.library.pulltorefresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 当前类注释:
 * 作者：jinwenfeng on 2017/5/9 15:03
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2017 jinwenfeng
 * © 版权所有，未经允许不得传播
 */

public class ViewStatus {

    @IntDef({CONTENT_STATUS, LOADING_STATUS,EMPTY_STATUS,ERROR_STATUS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VIEW_STATUS {

    }

    public static final int CONTENT_STATUS = 0;
    public static final int LOADING_STATUS = 1;
    public static final int EMPTY_STATUS = 2;
    public static final int ERROR_STATUS = 3;

}
