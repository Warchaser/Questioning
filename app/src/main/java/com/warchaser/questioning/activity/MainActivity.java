package com.warchaser.questioning.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.warchaser.questioning.util.AssetsUtil;
import com.warchaser.questioning.util.GsonUtil;
import com.warchaser.questioning.wiget.QuestioningAdapter;
import com.warchaser.questioning.bean.QuestioningBean;
import com.warchaser.questioning.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private QuestioningAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize(){

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new QuestioningAdapter(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        String jsonString = AssetsUtil.getDemoData("json.json");

        ArrayList<QuestioningBean> list = GsonUtil.parseString2List(jsonString, QuestioningBean.class);

        mAdapter.notifyDataSetAllChanged(list);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
