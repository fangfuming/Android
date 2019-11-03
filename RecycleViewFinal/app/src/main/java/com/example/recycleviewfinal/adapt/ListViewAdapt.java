package com.example.recycleviewfinal.adapt;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recycleviewfinal.R;
import com.example.recycleviewfinal.entity.ItemEntity;
import java.util.List;

public class ListViewAdapt extends RecycleViewBaseAdapter {

    //普通的条目
    private static final int TYPE_NOMAL = 0;
    //加载更多
    private static final int TYPE_LOADER_MORE = 1;
    private final List<ItemEntity> mDatas;
    private OnUpPullListener mOnUpPullListener;

    public ListViewAdapt(List<ItemEntity> datas) {
        super(datas);
        this.mDatas = datas;
    }

    //重写onCreateHolder方法(因为有多种类型了)，要返回不同类型的holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        if (viewType == TYPE_NOMAL) {
            return new InnerViewHolder(view);
        } else {
            return new LoadMoreViewHolder(view);
        }
    }

    //总的项数
    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size() + 1;
        }
        return 0;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NOMAL && holder instanceof InnerViewHolder) {
            super.onBindViewHolder(holder, position);
        } else if (getItemViewType(position) == TYPE_LOADER_MORE && holder instanceof LoadMoreViewHolder) {
            ((LoadMoreViewHolder) holder).updateState(LoadMoreViewHolder.LOADER_STATE_LOADING);
        }
    }

    @Override
    public View getSubView(ViewGroup parent, int viewType) {
        View view;
        //根据类型来创建不同的view
        if (viewType == TYPE_NOMAL) {
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_loader_more_view, null);
        }
        return view;
    }

    //重写getItemViewType条目类型方法
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //返回加载更多类型
            return TYPE_LOADER_MORE;
        }
        //返回普通类型
        return TYPE_NOMAL;
    }

    //向外暴露上拉加载更多方法
    public void setOnPullRefresh(OnUpPullListener listener) {
        this.mOnUpPullListener = listener;
    }

    //定义接口
    public interface OnUpPullListener {
        void upPullRefresh(LoadMoreViewHolder holder);
    }

    //定义一个加载更多holder
    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;
        private LinearLayout mLoading;
        private TextView mReload;

        LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            mLoading = itemView.findViewById(R.id.loading);
            mReload = itemView.findViewById(R.id.reload);
            mReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateState(LOADER_STATE_LOADING);
                }
            });
        }

       public void updateState(int state) {
            //重置控件的状态
            mLoading.setVisibility(View.GONE);
            mReload.setVisibility(View.GONE);
            switch (state) {
                case LOADER_STATE_LOADING:
                    mLoading.setVisibility(View.VISIBLE);
                    //触发加载数据
                    if (mOnUpPullListener != null) {
                        mOnUpPullListener.upPullRefresh(LoadMoreViewHolder.this);
                    }
                    break;
                case LOADER_STATE_RELOAD:
                    mReload.setVisibility(View.VISIBLE);
                    //触发加载数据
                    break;
                case LOADER_STATE_NORMAL:
                    mLoading.setVisibility(View.GONE);
                    mReload.setVisibility(View.GONE);
                    break;
            }
        }
    }
}
