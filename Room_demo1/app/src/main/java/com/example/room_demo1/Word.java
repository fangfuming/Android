package com.example.room_demo1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "en_word")
    private String word;
    @ColumnInfo(name = "chi_meaning")
    private String chineseMeaning;

    @ColumnInfo(name = "hide_chinese")
    private boolean hideChinese;

    public boolean isHideChinese() {
        return hideChinese;
    }

    public void setHideChinese(boolean hideChinese) {
        this.hideChinese = hideChinese;
    }

    Word(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }

     int getId() {
        return id;
    }

     void setId(int id) {
        this.id = id;
    }

     String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    String getChineseMeaning() {
        return chineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        this.chineseMeaning = chineseMeaning;
    }
}
