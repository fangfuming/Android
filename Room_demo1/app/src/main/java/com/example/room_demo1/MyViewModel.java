package com.example.room_demo1;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private WordRepository wordRepository;

     LiveData<List<Word>> getAllWords() {
        return wordRepository.getAllWords();
    }

    public MyViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);

    }

    void insertWords(Word... words){
        wordRepository.insertWords(words);
    }

    void updateWords(Word... words){
        wordRepository.updateWords(words);
    }

    void deleteWords(Word... words){
        wordRepository.deleteWords(words);
    }

    void clearWords(){
        wordRepository.clearWords();
    }
}
