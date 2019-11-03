package com.example.recycleviewfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.recycleviewfinal.adapt.GridViewAdapter;
import com.example.recycleviewfinal.adapt.ListViewAdapt;
import com.example.recycleviewfinal.adapt.RecycleViewBaseAdapter;
import com.example.recycleviewfinal.adapt.StaggerAdapter;
import com.example.recycleviewfinal.entity.Datas;
import com.example.recycleviewfinal.entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.recyclerview.widget.StaggeredGridLayoutManager.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ItemEntity> mData;
    private RecycleViewBaseAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);
        swipeRefreshLayout = findViewById(R.id.refresh);
        //模拟数据
        initData();
        //展示数据
        showList(true, false);
        //处理下拉刷新
        handlerDownPullUpdate();
    }


    private void handlerDownPullUpdate() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //todo 执行刷新数据的操作
                /**
                 * 当我们在顶部下拉的时候，这个方法会触发
                 * 这是主线程，不可以执行耗时操作
                 * 请求网络要开启线程去获取
                 * 这个demo我直接添加一条数据
                 */
                //添加数据
                ItemEntity itemEntity = new ItemEntity();
                itemEntity.icon = R.drawable.pic_01;
                itemEntity.title = "我是刷新出来的数据";
                mData.add(0, itemEntity);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //两件事
                        //第一 停止刷新，第二 列表更新
                        adapt.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false); //停止刷新
                    }
                }, 3000);
            }
        });
    }

    private void initData() {
        //List<ItemEntity>  ->Adapter ->显示数据
        mData = new ArrayList<>();
        //模拟创建数据
        for (int i = 0; i < 10; i++) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.icon = Datas.icons[i];
            itemEntity.title = "我是标题" + i;
            mData.add(itemEntity);
        }
        adapt = new ListViewAdapt(mData);
    }

    void initListener() {
        adapt.setOnItemclickListener(new RecycleViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "这是第" + position + "条", Toast.LENGTH_SHORT).show();
            }
        });
        if (adapt instanceof ListViewAdapt) {

        }
    }

    /**
     * 初始化选项菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单某项被选中(给菜单项设置事件)
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.list_view_vertical_stander:
                Log.d("hh", "垂直标准");
                showList(true, false);
                break;
            case R.id.list_view_vertical_reverse:
                Log.d("hh", "垂直反向");
                showList(true, true);
                break;
            case R.id.list_view_horizonal_stander:
                Log.d("hh", "水平标准");
                showList(false, false);
                break;
            case R.id.list_view_horizonal_reverse:
                Log.d("hh", "水平反向");
                showList(false, true);
                break;
            case R.id.grid_view_vertical_stander:
                showGrid(true, false);
                break;
            case R.id.grid_view_vertical_reverse:
                showGrid(true, true);
                break;
            case R.id.grid_view_horizonal_stander:
                showGrid(false, false);
                break;
            case R.id.grid_view_horizonal_reverse:
                showGrid(false, true);
                break;
            case R.id.stargger_view_vertical_stander:
                showStagger(true, false);
                break;
            case R.id.stargger_view_vertical_reverse:
                showStagger(true, true);
                break;
            case R.id.stargger_view_horizonal_stander:
                showStagger(false, false);
                break;
            case R.id.stargger_view_horizonal_reverse:
                showStagger(false, true);
                break;
            case R.id.more_type:
                Intent intent = new Intent(this, MoreTypeActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showList(boolean isVertical, boolean isReverse) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器（非常重要）
        recyclerView.setAdapter(adapt);
        initListener();
    }

    private void showStagger(boolean isVertical, boolean isReverse) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, isVertical ? VERTICAL : HORIZONTAL);
        staggeredGridLayoutManager.setReverseLayout(isReverse);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapt = new StaggerAdapter(mData);
        recyclerView.setAdapter(adapt);
        initListener();
    }

    private void showGrid(boolean isVertical, boolean isReverse) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        gridLayoutManager.setReverseLayout(isReverse);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapt = new GridViewAdapter(mData);
        recyclerView.setAdapter(adapt);
        initListener();
    }
}