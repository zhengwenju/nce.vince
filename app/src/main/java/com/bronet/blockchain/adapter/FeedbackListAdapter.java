package com.bronet.blockchain.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 18514 on 2019/7/26.
 */

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {
    BaseActivity activity;
    ArrayList<Feedback.Data> list;

    public FeedbackListAdapter(OnItemChildClickListener mOnItemChildClickListener, ArrayList<Feedback.Data> list, BaseActivity activity) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
        this.list=list;
        this.activity=activity;
    }

    private OnItemChildClickListener mOnItemChildClickListener;
    public interface OnItemChildClickListener {
        void onItemShowClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.item_fl, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.setIsRecyclable(false);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Feedback.Data data = list.get(position);
        if(data.getTypeId()==0){ //问
            holder.feedbackLayout.setVisibility(View.VISIBLE);
            holder.mFeedbackTimeTv.setVisibility(View.VISIBLE);
            holder.mFeedbackImageIv.setVisibility(View.VISIBLE);

            holder.mAnswerLayout.setVisibility(View.GONE);
            holder.mAnswerTimeTv.setVisibility(View.GONE);
            holder.mAnswerImageIv.setVisibility(View.GONE);

            Glide.with(activity).load(data.getUserAvatar()).apply(new RequestOptions().circleCrop().error(R.mipmap.dicon_37)).into(holder.mFeedbackAvaIv);
            holder.mFeedbackTimeTv.setText(data.getCreateTime());
            if(!TextUtils.isEmpty(data.getFeedback())){
                holder.mFeedbackContentTv.setText(data.getFeedback());
            }
            if (!TextUtils.isEmpty(data.getFujian())){
                Glide.with(activity).load(data.getFujian()).into(holder.mFeedbackImageIv);
                holder.mFeedbackImageIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemChildClickListener.onItemShowClick(holder.getAdapterPosition());
                    }
                });
                holder.mFeedbackImageIv.setVisibility(View.VISIBLE);
            }else {
                holder.mFeedbackImageIv.setVisibility(View.GONE);
            }

        }else { //答
            holder.feedbackLayout.setVisibility(View.GONE);
            holder.mFeedbackTimeTv.setVisibility(View.GONE);
            holder.mFeedbackImageIv.setVisibility(View.GONE);

            holder.mAnswerLayout.setVisibility(View.VISIBLE);
            holder.mAnswerTimeTv.setVisibility(View.VISIBLE);
            holder.mAnswerImageIv.setVisibility(View.VISIBLE);

            Glide.with(activity).load(data.getSysAvatar()).apply(new RequestOptions().circleCrop().error(R.mipmap.logo)).into(holder.mAnswerAvaIv);
            holder.mAnswerTimeTv.setText(data.getCreateTime());
            if(!TextUtils.isEmpty(data.getAnswer())){
                holder.mAnswerContentTv.setText(data.getAnswer());
                holder.mAnswerContentTv.setVisibility(View.VISIBLE);
            }else {
                holder.mAnswerContentTv.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(data.getFujian())){
                Glide.with(activity).load(data.getFujian()).into(holder.mAnswerImageIv);
                holder.mAnswerImageIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemChildClickListener.onItemShowClick(holder.getAdapterPosition());
                    }
                });
                holder.mAnswerImageIv.setVisibility(View.VISIBLE);
            }else {
                holder.mAnswerImageIv.setVisibility(View.GONE);
            }

        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNewData(List<Feedback.Data> data,RecyclerView recyclerView) {
      try {
          list.addAll(0, data);
          notifyDataSetChanged();
          if(list.size()<=data.size()) {
              recyclerView.scrollToPosition(-1);
          }else {
              recyclerView.scrollToPosition(data.size()+1);
          }
      }catch (Exception ex){
            ex.printStackTrace();
      }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.feedback_time_tv)
        TextView mFeedbackTimeTv;
        @BindView(R.id.feedback_content_tv)
        TextView mFeedbackContentTv;
        @BindView(R.id.feedback_ava_iv)
        ImageView mFeedbackAvaIv;
        @BindView(R.id.feedback_layout)
        RelativeLayout feedbackLayout;
        @BindView(R.id.feedback_image_iv)
        ImageView mFeedbackImageIv;
        @BindView(R.id.answer_time_tv)
        TextView mAnswerTimeTv;
        @BindView(R.id.answer_ava_iv)
        ImageView mAnswerAvaIv;
        @BindView(R.id.answer_content_tv)
        TextView mAnswerContentTv;
        @BindView(R.id.answer_layout)
        RelativeLayout mAnswerLayout;
        @BindView(R.id.answer_image_iv)
        ImageView mAnswerImageIv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
