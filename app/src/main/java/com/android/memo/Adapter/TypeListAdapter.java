package com.android.memo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.memo.Bean.NoteBean;
import com.android.memo.Bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

import static com.android.memo.R.drawable;
import static com.android.memo.R.id;
import static com.android.memo.R.layout;

/**
 * RecyclerView适配器
 */

public class TypeListAdapter extends RecyclerView.Adapter<TypeListAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context mContext;
    private List<TypeBean> mTypeBeans;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private OnLoginClick onAlarmClickListener;
    private int position;

    public void setOnItemLongClick(OnLoginClick onAlarmClickListener) {
        this.onAlarmClickListener = onAlarmClickListener;
    }


    public TypeListAdapter() {
        mTypeBeans = new ArrayList<>();
    }

    public void setTypes(List<TypeBean> notes) {
        this.mTypeBeans = notes;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (NoteBean) v.getTag());
        }
    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, NoteBean note);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.i(TAG, "###onCreateViewHolder: ");
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(layout.list_item_type, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        //view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Log.i(TAG, "###onBindViewHolder: ");
        final TypeBean typeBean = mTypeBeans.get(position);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.tvType.setText(typeBean.getTitle());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onAlarmClickListener.onClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.i(TAG, "###getItemCount: ");
        if (mTypeBeans != null && mTypeBeans.size() > 0) {
            return mTypeBeans.size();
        }
        return 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvType;


        public ViewHolder(View view) {
            super(view);
            tvType = (TextView) view.findViewById(id.tv_type);


        }


    }

    public interface OnLoginClick {
        void onClick(int position);
    }
}
