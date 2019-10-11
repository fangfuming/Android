package com.example.room_demo1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface WordDao {
    @Insert
    void insertWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Query("delete from WORD")
    void deleteAllWords();

    @Query("select * from WORD order by id desc")
//    List<Word> queryWord();
    LiveData<List<Word>>  getAllWordsLive();
}

