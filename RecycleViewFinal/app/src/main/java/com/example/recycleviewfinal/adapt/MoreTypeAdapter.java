package com.example.recycleviewfinal.adapt;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewfinal.R;
import com.example.recycleviewfinal.entity.MoreTypeEntity;

import java.util.List;

public class MoreTypeAdapter extends RecyclerView.Adapter {

    private final List<MoreTypeEntity> mDatas;
    //定义三个常量标识三种类型
    private static final int TYPE_FULL_IMAGE = 0;
    private static final int TYPE_RIGHT_IMAGE = 1;
    private static final int TYPE_THREE_IMAGE = 2;

    public MoreTypeAdapter(List<MoreTypeEntity> datas) {
        this.mDatas = datas;
    }

    //覆写一个方法，返回的是条目类型
    @Override
    public int getItemViewType(int position) {
        MoreTypeEntity moreTypeEntity = mDatas.get(position);
        if (moreTypeEntity.type == 0) {
            return TYPE_FULL_IMAGE;
        } else if (moreTypeEntity.type == 1) {
            return TYPE_RIGHT_IMAGE;
        } else {
            return TYPE_THREE_IMAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO 创建三种类型的view Holder
        switch (viewType) {
            case TYPE_FULL_IMAGE:
                return new FullImageHolder(View.inflate(parent.getContext(), R.layout.full_image, null));
            case TYPE_THREE_IMAGE:
                return new ThreeImageHolder(View.inflate(parent.getContext(), R.layout.three_image, null));
            case TYPE_RIGHT_IMAGE:
            default:
                return new RightImageHolder(View.inflate(parent.getContext(), R.layout.right_image, null));
        }
    }

    //设置数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 0){
            FullImageHolder holder1 = (FullImageHolder)holder;
            holder1.imageView.setImageResource(mDatas.get(position).icon);
        }else if(getItemViewType(position) == 1){
            RightImageHolder holder1 = (RightImageHolder)holder;
            holder1.imageView.setImageResource(mDatas.get(position).icon);
        }else {
            ThreeImageHolder holder1 = (ThreeImageHolder)holder;
            holder1.imageView.setImageResource(mDatas.get(position).icon);
        }
    }

    @Override
    public int getItemCount() {
        if (this.mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    //给holder初始化控件，这里就不做了
    private class FullImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
         FullImageHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RightImageHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.imageView);
         }
    }

    private class ThreeImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ThreeImageHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
