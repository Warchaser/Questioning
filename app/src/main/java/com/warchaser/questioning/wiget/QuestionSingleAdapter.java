package com.warchaser.questioning.wiget;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.warchaser.questioning.bean.QuestioningBean;
import com.warchaser.questioning.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class QuestionSingleAdapter extends RecyclerView.Adapter<QuestionSingleAdapter.VH> {

    private final int REFRESH_LIST = 1;

    private ArrayList<QuestioningBean.Question> mDataList = new ArrayList<>();

    private Object mTag;
    private int mIndex = -1;

    private OnSingleItemClickListener mOnSingleItemClickListener;
    private OnItemClickListenerInner mOnItemClickListenerInner;

    private RefreshHandler mHandler;

    private Runnable mRunnable;

    public QuestionSingleAdapter(){
        mOnItemClickListenerInner = new OnItemClickListenerInner();
        mHandler = new RefreshHandler(this);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        };
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
        return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_single_selection, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        QuestioningBean.Question bean = mDataList.get(i);
        vh.mTvQuestionTitle.setText(bean.getTitle());
        vh.mRb.setTag(i);
        vh.mRb.setOnCheckedChangeListener(mOnItemClickListenerInner);

        vh.mRb.setChecked(mIndex == vh.getAdapterPosition());

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

    public void setOnSingleItemClickListener(OnSingleItemClickListener listener){
        mOnSingleItemClickListener = listener;
    }

    private static class RefreshHandler extends Handler {

        private WeakReference<QuestionSingleAdapter> mWeakReference;


        RefreshHandler(QuestionSingleAdapter adapter){
            mWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    }

    private class OnItemClickListenerInner implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            final int position = (int)buttonView.getTag();
            if(isChecked){
                mIndex = position;
                mHandler.post(mRunnable);

                switch (buttonView.getId()){
                    case R.id.mCb:
                        mDataList.get(position).setSelected(((RadioButton)buttonView).isChecked());
                        if(mOnSingleItemClickListener != null){
                            mOnSingleItemClickListener.onItemClick((int)mTag, position, mDataList.get(position));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public interface OnSingleItemClickListener{
        void onItemClick(int groupPosition, int childPosition, QuestioningBean.Question bean);
    }

    public void clear(){
        if(mDataList != null){
            mDataList.clear();
            mDataList = null;
        }

        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        mRunnable = null;
    }

    class VH extends RecyclerView.ViewHolder{

        TextView mTvQuestionTitle;
        RadioButton mRb;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvQuestionTitle = (TextView)itemView.findViewById(R.id.mTvQuestionTitle);
            mRb = (RadioButton)itemView.findViewById(R.id.mCb);
        }
    }

}
