package com.example.recycleviewfinal.adapt;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewfinal.R;
import com.example.recycleviewfinal.entity.ItemEntity;

import java.util.List;

public abstract class RecycleViewBaseAdapter extends RecyclerView.Adapter {
    private List<ItemEntity> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemclickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    RecycleViewBaseAdapter(List<ItemEntity> datas) {
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //第一步：拿到每一项的view
        //第二步：创建viewHolder
        //需要传递的参数view是每一项的视图
        View view = getSubView(parent,viewType);

        return new InnerViewHolder(view);
    }

    public abstract View getSubView(ViewGroup parent,int viewType);

    /**
     * 绑定holder,一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((InnerViewHolder)holder).setData(mDatas.get(position), position);
    }

    //总的项数
    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    //自定义的viewHolder
    public class InnerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private int mPosition;

        InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_icon);
            textView = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        void setData(ItemEntity itemEntity, int position) {
            this.mPosition = position;
            imageView.setImageResource(itemEntity.icon);
            textView.setText(itemEntity.title);
        }
    }
}
