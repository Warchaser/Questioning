package com.warchaser.questioning.wiget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.warchaser.questioning.bean.QuestioningBean;
import com.warchaser.questioning.R;

import java.util.ArrayList;

public class QuestionMultipleAdapter extends RecyclerView.Adapter<QuestionMultipleAdapter.VH> {

    private ArrayList<QuestioningBean.Question> mDataList = new ArrayList<>();

    private Object mTag;

    private OnMultipleItemClickListener mOnMultipleItemClickListener;

    private OnItemClickListenerInner mOnItemClickListenerInner;

    public QuestionMultipleAdapter(){
        mOnItemClickListenerInner = new OnItemClickListenerInner();
    }

    public void notifyDataSetAllChanged(ArrayList<QuestioningBean.Question> list){
        if(mDataList != null){
            mDataList.clear();
            mDataList.addAll(list);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_multiple_selection, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        QuestioningBean.Question bean = mDataList.get(i);
        vh.mTvQuestionTitle.setText(bean.getTitle());
        vh.mCb.setTag(i);
        vh.mCb.setOnClickListener(mOnItemClickListenerInner);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public void setOnMultipleItemClickListener(OnMultipleItemClickListener listener){
        mOnMultipleItemClickListener = listener;
    }

    private class OnItemClickListenerInner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            final int position = (int)v.getTag();
            switch (v.getId()){
                case R.id.mCb:
                    mDataList.get(position).setSelected(((CheckBox)v).isChecked());
                    if(mOnMultipleItemClickListener != null){
                        mOnMultipleItemClickListener.onItemClick((int)mTag, position, mDataList.get(position));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnMultipleItemClickListener{

        void onItemClick(int groupPosition, int childPosition, QuestioningBean.Question bean);

    }

    class VH extends RecyclerView.ViewHolder{

        TextView mTvQuestionTitle;
        CheckBox mCb;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvQuestionTitle = (TextView)itemView.findViewById(R.id.mTvQuestionTitle);
            mCb = (CheckBox)itemView.findViewById(R.id.mCb);
        }
    }



}
