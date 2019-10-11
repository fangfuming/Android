package com.example.room_demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonInsert,buttonDelete,buttonUpdate,buttonClear;
    MyViewModel myViewModel;
    RecyclerView recyclerView;
    MyAdapt myAdapt1,myAdapt2;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView2);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);
        myAdapt1 = new MyAdapt(false,myViewModel);
        myAdapt2 = new MyAdapt(true,myViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapt1);
        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    recyclerView.setAdapter(myAdapt2);
                }else {
                    recyclerView.setAdapter(myAdapt1);
                }
            }
        });

        //myViewModel.getAllWords()为liveData,添加观察者
        myViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapt1.getItemCount();
                myAdapt1.setAllWords(words);
                myAdapt2.setAllWords(words);
                if(temp != words.size()){
                    myAdapt1.notifyDataSetChanged();
                    myAdapt2.notifyDataSetChanged();
                }
            }
        });
        buttonInsert = findViewById(R.id.insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("hello", "你好");
                Word word2 = new Word("name", "名字");
                Word word3 = new Word("world", "世界");
                Word word4 = new Word("view", "视图");
                Word word5 = new Word("Value", "价值");
                Word word6 = new Word("mobile", "手机");
                Word word7 = new Word("computer", "电脑");
                Word word8 = new Word("morning", "早上");
                Word word9 = new Word("paper", "纸张");
                Word word10 = new Word("bed", "床");
                myViewModel.insertWords(word1,word2,word3,word4,word5,word6,word7,word8,word9,word10);
            }
        });
        buttonClear = findViewById(R.id.deleteAll);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.clearWords();
            }
        });
    }
}
