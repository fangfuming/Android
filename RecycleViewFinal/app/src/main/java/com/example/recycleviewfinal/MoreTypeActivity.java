package com.example.recycleviewfinal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewfinal.adapt.MoreTypeAdapter;
import com.example.recycleviewfinal.entity.Datas;
import com.example.recycleviewfinal.entity.MoreTypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreTypeActivity extends AppCompatActivity {
    private List<MoreTypeEntity> mDatas = new ArrayList<>();
    RecyclerView recyclerView;
    MoreTypeAdapter moreTypeAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_type);
        recyclerView = findViewById(R.id.recycle_view);
        Random random = new Random();
        //初始化数据
        for(int i=0;i<10;i++){
            MoreTypeEntity moreTypeEntity = new MoreTypeEntity();
            moreTypeEntity.icon = Datas.icons[i];
            moreTypeEntity.type = random.nextInt(3);
            mDatas.add(moreTypeEntity);
        }
        //创建和设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建和设置适配器
        moreTypeAdapter = new MoreTypeAdapter(mDatas);
        recyclerView.setAdapter(moreTypeAdapter);
    }
}
