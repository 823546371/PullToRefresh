package com.jwenfeng.pulltorefresh;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.State;
import com.jwenfeng.library.pulltorefresh.ViewStatus;
import com.jwenfeng.pulltorefresh.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

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
}
