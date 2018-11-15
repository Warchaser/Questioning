package com.warchaser.questioning.wiget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warchaser.questioning.bean.QuestioningBean;
import com.warchaser.questioning.R;

import java.util.ArrayList;

public class QuestioningAdapter extends RecyclerView.Adapter<QuestioningAdapter.VH> {

    private Context mContext;
    private ArrayList<QuestioningBean> mDataList = new ArrayList<>();
    private final int TYPE_MULTIPLE = 2;
    private final int TYPE_SINGLE = 1;
    private QuestionSingleAdapter.OnSingleItemClickListener mOnSingleItemClickListener;
    private QuestionMultipleAdapter.OnMultipleItemClickListener mOnMultipleItemClickListener;

    public QuestioningAdapter(Context context){
        this.mContext = context;

        mOnSingleItemClickListener = new QuestionSingleAdapter.OnSingleItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition, QuestioningBean.Question bean) {
                mDataList.get(groupPosition).setCurrentSelected(childPosition);
            }
        };

        mOnMultipleItemClickListener = new QuestionMultipleAdapter.OnMultipleItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition, QuestioningBean.Question bean) {
                mDataList.get(groupPosition).getQuestions().get(childPosition).setSelected(bean.isSelected());
            }
        };
    }

    public void notifyDataSetAllChanged(ArrayList<QuestioningBean> list){
        if(mDataList != null){
            mDataList.clear();
            mDataList.addAll(list);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_container, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        final QuestioningBean bean = mDataList.get(i);
        final int type = bean.getType();
        final int tag = i;
        if(TYPE_MULTIPLE == type){
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            QuestionMultipleAdapter adapter = new QuestionMultipleAdapter();
            adapter.setTag(tag);
            adapter.setOnMultipleItemClickListener(mOnMultipleItemClickListener);
            vh.mRecyclerView.setLayoutManager(manager);
            vh.mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetAllChanged(bean.getQuestions());
            vh.mTvTitle.setText(bean.getTitle());

        } else if(TYPE_SINGLE == type){
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            QuestionSingleAdapter adapter = new QuestionSingleAdapter();
            adapter.setTag(tag);
            adapter.setOnSingleItemClickListener(mOnSingleItemClickListener);
            vh.mRecyclerView.setLayoutManager(manager);
            vh.mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetAllChanged(bean.getQuestions());
            vh.mTvTitle.setText(bean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView mTvTitle;
        RecyclerView mRecyclerView;

        public VH(@NonNull View itemView) {
            super(itemView);

            mTvTitle = (TextView) itemView.findViewById(R.id.mTvTitle);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.mRecyclerView);
        }
    }
}
