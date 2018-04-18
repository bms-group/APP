package com.asyf.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asyf.app.R;
import com.asyf.app.common.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/18.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyHolder> {

    private ArrayList<String> mData;
    private final String TAG = "RecycleViewAdapter";

    /**
     * 事件回调监听
     */
    private OnItemClickListener onItemClickListener;

    public RecycleViewAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_view, parent, false);
        // 实例化viewholder
        MyHolder viewHolder = new MyHolder(view);
        return viewHolder;
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(RecycleViewAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        Logger.e(TAG, holder.itemView.toString());
        // 绑定数据
        holder.mTv.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public MyHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

}
