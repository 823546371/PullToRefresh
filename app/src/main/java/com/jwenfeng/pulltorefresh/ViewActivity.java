package com.jwenfeng.pulltorefresh;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.ViewStatus;
import com.jwenfeng.pulltorefresh.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.activity_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add("PullToRefreshLayout"+i);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshLayout.showView(ViewStatus.LOADING_STATUS);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pullToRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                                initError();
                            }
                        },2000);
                    }
                },2000);
            }
        },2000);


        adapter = new RecyclerViewAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshLayout.finishRefresh();
                    }
                },2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });


    }

    private void initError() {
        // 获取页面
        View error = pullToRefreshLayout.getView(ViewStatus.ERROR_STATUS);

        error.findViewById(R.id.error_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullToRefreshLayout.showView(ViewStatus.LOADING_STATUS);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                    }
                },2000);
            }
        });
    }
}
