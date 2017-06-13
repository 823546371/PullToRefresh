package com.jwenfeng.pulltorefresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lstv,recycler,web,scroll,view,tbsWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstv = (TextView) findViewById(R.id.main_list_view);
        recycler = (TextView) findViewById(R.id.main_recycler_view);
        web = (TextView) findViewById(R.id.main_web_view);
        scroll = (TextView) findViewById(R.id.main_scroll_view);
        view = (TextView) findViewById(R.id.main_view);
        tbsWeb = (TextView) findViewById(R.id.main_tbs_web_view);

        lstv.setOnClickListener(this);
        recycler.setOnClickListener(this);
        web.setOnClickListener(this);
        scroll.setOnClickListener(this);
        view.setOnClickListener(this);
        tbsWeb.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_list_view:
                startActivity(new Intent(this,ListActivity.class));
                break;
            case R.id.main_recycler_view:
                startActivity(new Intent(this,RecyclerViewActivity.class));
                break;
            case R.id.main_scroll_view:
                startActivity(new Intent(this,ScrollViewActivity.class));
                break;
            case R.id.main_web_view:
                startActivity(new Intent(this,WebviewActivity.class));
                break;
            case R.id.main_view:
                startActivity(new Intent(this,ViewActivity.class));
                break;
            case R.id.main_tbs_web_view:
                startActivity(new Intent(this,TBSWebViewActivity.class));
                break;
        }
    }
}
