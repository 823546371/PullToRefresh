package com.jwenfeng.library.pulltorefresh;

/**
 * Created by jiang on 16/8/18.
 */

public interface BaseRefreshListener {


    /**
     * 刷新
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();
}
