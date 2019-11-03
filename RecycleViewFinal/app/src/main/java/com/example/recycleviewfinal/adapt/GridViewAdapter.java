package com.example.recycleviewfinal.adapt;

import android.view.View;
import android.view.ViewGroup;
import com.example.recycleviewfinal.R;
import com.example.recycleviewfinal.entity.ItemEntity;

import java.util.List;

public class GridViewAdapter extends RecycleViewBaseAdapter {

    public GridViewAdapter(List<ItemEntity> datas) {
        super(datas);
    }

    @Override
    public View getSubView(ViewGroup parent,int viewType) {
        return View.inflate(parent.getContext(),R.layout.item_grid_view,null);
    }
}
