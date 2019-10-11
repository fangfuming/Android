package com.example.room_demo1;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapt extends RecyclerView.Adapter<MyAdapt.MyViewHolder> {
    private List<Word>  allWords = new ArrayList<>();
    private boolean useCardView;
    private MyViewModel myViewModel;

    public MyAdapt(boolean useCardView,MyViewModel myViewModel){
        this.useCardView = useCardView;
        this.myViewModel = myViewModel;
    }
    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView ;
        if(useCardView){
            itemView = inflater.inflate(R.layout.item_card2,parent,false);
        }else{
            itemView = inflater.inflate(R.layout.item_normal2,parent,false);
        }
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        //可回收的view会重用视图造成bug，原来的视图有监听器
        holder.aSwitch.setOnCheckedChangeListener(null);//需要注意一下，等数据显示结束后再绑定监听器
        if(word.isHideChinese()){
//            holder.textViewChinese.setVisibility(View.GONE);
            holder.textViewChinese.setVisibility(View.INVISIBLE);
            holder.aSwitch.setChecked(true);
        }else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q="+holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                v.getContext().startActivity(intent);
            }
        });
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    holder.textViewChinese.setVisibility(View.GONE);         View.GONE不占据空间
                    holder.textViewChinese.setVisibility(View.INVISIBLE);
                    word.setHideChinese(true);
                    myViewModel.updateWords(word);
                }else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setHideChinese(false);
                    myViewModel.updateWords(word);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    //自定义一个viewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewEnglish,textViewChinese,textViewNumber;
        Switch aSwitch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEnglish = itemView.findViewById(R.id.textView);
            textViewChinese = itemView.findViewById(R.id.textView2);
            textViewNumber = itemView.findViewById(R.id.textView4);
            aSwitch = itemView.findViewById(R.id.switch2);

        }
    }
}
