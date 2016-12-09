package com.jwenfeng.library.pulltorefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jwenfeng.library.pulltorefresh.view.FooterView;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView;
import com.jwenfeng.library.pulltorefresh.view.HeadView;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;

/**
 * 当前类注释:
 * 作者：jinwenfeng on 2016/12/6 11:52
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * © 版权所有，未经允许不得传播
 */
public class PullToRefreshLayout extends FrameLayout {

    private HeadView mHeaderView;
    private FooterView mFooterView;
    private View mChildView;

    private static final long ANIM_TIME = 250;
    private static int HEAD_HEIGHT = 60;
    private static int FOOT_HEIGHT = 60;

    private static int head_height;
    private static int head_height_2;
    private static int foot_height;
    private static int foot_height_2;

    private float mTouchY;
    private float mCurrentY;

    private boolean canLoadMore = true;
    private boolean canRefresh = true;
    private boolean isRefresh;
    private boolean isLoadMore;

    private BaseRefreshListener refreshListener;

    public void setRefreshListener(BaseRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void cal() {
        head_height = Dp2Px(getContext(), HEAD_HEIGHT);
        foot_height = Dp2Px(getContext(), FOOT_HEIGHT);
        head_height_2 = Dp2Px(getContext(), HEAD_HEIGHT * 2);
        foot_height_2 = Dp2Px(getContext(), FOOT_HEIGHT * 2);
    }

    private void init() {
        cal();
        int count = getChildCount();
        if (count != 1) {
            new IllegalArgumentException("child only can be one");
        }
    }

    public void setHeaderView(HeadView mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public void setFooterView(FooterView mFooterView) {
        this.mFooterView = mFooterView;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mChildView = getChildAt(0);
        addHeadView();
        addFooterView();
    }

    private void addHeadView() {
        if (mHeaderView == null) {
            mHeaderView = new HeadRefreshView(getContext());
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        mHeaderView.getView().setLayoutParams(layoutParams);
        if (mHeaderView.getView().getParent() != null)
            ((ViewGroup) mHeaderView.getView().getParent()).removeAllViews();
        addView(mHeaderView.getView(), 0);
    }

    private void addFooterView() {
        if (mFooterView == null) {
            mFooterView = new LoadMoreView(getContext());
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.BOTTOM;
        mFooterView.getView().setLayoutParams(layoutParams);
        if (mFooterView.getView().getParent() != null)
            ((ViewGroup) mFooterView.getView().getParent()).removeAllViews();
        addView(mFooterView.getView());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!canLoadMore && !canRefresh) return super.onInterceptTouchEvent(ev);
        if (isRefresh || isLoadMore) return true;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mCurrentY;
                if (canRefresh) {
                    boolean canChildScrollUp = canChildScrollUp();
                    if (dy > 0 && !canChildScrollUp) {
                        mHeaderView.begin();
                        return true;
                    }
                }
                if (canLoadMore) {
                    boolean canChildScrollDown = canChildScrollDown();
                    if (dy < 0 && !canChildScrollDown) {
                        mFooterView.begin();
                        return true;
                    }
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isRefresh || isLoadMore) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = event.getY();
                float dura = (mCurrentY - mTouchY)/4.0f;
                if (dura > 0 && canRefresh) {
                    dura = Math.min(head_height_2, dura);
                    dura = Math.max(0, dura);
                    mHeaderView.getView().getLayoutParams().height = (int) dura;
                    ViewCompat.setTranslationY(mChildView, dura);
                    requestLayout();
                    mHeaderView.progress(dura, head_height);
                } else {
                    if (canLoadMore) {
                        dura = Math.min(foot_height_2, Math.abs(dura));
                        dura = Math.max(0, Math.abs(dura));
                        mFooterView.getView().getLayoutParams().height = (int) dura;
                        ViewCompat.setTranslationY(mChildView, -dura);
                        requestLayout();
                        mFooterView.progress(dura, foot_height);
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float currentY = event.getY();
                final int dy1 = (int) (currentY - mTouchY)/4;
                if (dy1 > 0 && canRefresh) {
                    if (dy1 >= head_height) {
                        createAnimatorTranslationY(State.REFRESH,
                                dy1 > head_height_2 ? head_height_2 : dy1, head_height,
                                new CallBack() {
                                    @Override
                                    public void onSuccess() {
                                        isRefresh = true;
                                        if (refreshListener != null) {
                                            refreshListener.refresh();
                                        }
                                        mHeaderView.loading();
                                    }
                                });
                    } else if (dy1 > 0 && dy1 < head_height) {
                        setFinish(dy1, State.REFRESH);
                        mHeaderView.normal();
                    }
                } else {
                    if (canLoadMore) {
                        if (Math.abs(dy1) >= foot_height) {
                            createAnimatorTranslationY(State.LOADMORE, Math.abs(dy1) > foot_height_2 ? foot_height_2 : Math.abs(dy1), foot_height, new CallBack() {
                                @Override
                                public void onSuccess() {
                                    isLoadMore = true;
                                    if (refreshListener != null) {
                                        refreshListener.loadMore();
                                    }
                                    mFooterView.loading();
                                }
                            });
                        } else {
                            setFinish(Math.abs(dy1), State.LOADMORE);
                            mFooterView.normal();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean canChildScrollDown() {
        if (mChildView == null) {
            return false;
        }

        return ViewCompat.canScrollVertically(mChildView, 1);
    }

    private boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        return ViewCompat.canScrollVertically(mChildView, -1);
    }

    /**
     * 创建动画
     */
    public void createAnimatorTranslationY(@State.REFRESH_STATE final int state, final int start, final int purpose, final CallBack callBack) {
        final ValueAnimator anim;
        anim = ValueAnimator.ofInt(start, purpose);
        anim.setDuration(ANIM_TIME);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                if (state == State.REFRESH) {
                    mHeaderView.getView().getLayoutParams().height = value;
                    ViewCompat.setTranslationY(mChildView, value);
                    if (purpose == 0) { //代表结束加载
                        mHeaderView.finishing(value, head_height_2);
                    } else {
                        mHeaderView.progress(value, head_height);
                    }
                } else {
                    mFooterView.getView().getLayoutParams().height = value;
                    ViewCompat.setTranslationY(mChildView, -value);
                    if (purpose == 0) { //代表结束加载
                        mFooterView.finishing(value, head_height_2);
                    } else {
                        mFooterView.progress(value, foot_height);
                    }
                }
                if (value == purpose) {
                    if (callBack != null)
                        callBack.onSuccess();
                }
                requestLayout();


            }

        });
        anim.start();
    }


    /**
     * 结束下拉刷新
     */
    private void setFinish(int height, @State.REFRESH_STATE final int state) {
        createAnimatorTranslationY(state, height, 0, new CallBack() {
            @Override
            public void onSuccess() {
                if (state == State.REFRESH) {
                    isRefresh = false;
                    mHeaderView.normal();
//                    if (refreshListener != null) {
//                        refreshListener.finish();
//                    }

                } else {
                    isLoadMore = false;
                    mFooterView.normal();
//                    if (refreshListener != null) {
//                        refreshListener.finishLoadMore();
//                    }
                }
            }
        });
    }

    public void setFinish(@State.REFRESH_STATE int state) {
        if (state == State.REFRESH) {
            if (mHeaderView != null && mHeaderView.getView().getLayoutParams().height > 0 && isRefresh) {
                setFinish(head_height, state);
            }
        } else {
            //这里因为canLoadMore不能真正的标志能不能加载更多,比如:在loadMore回调里调用
            /**
             *   refreshLayout.setLoadMore(false);
             *  refreshLayout.setFinish(State.LOADMORE);
             *  会出现问题
             */
            if (mFooterView != null && mFooterView.getView().getLayoutParams().height > 0 && isLoadMore) {
                setFinish(foot_height, state);
            }
        }
    }

    public interface CallBack {
        void onSuccess();
    }


    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
