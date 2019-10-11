package com.example.room_demo1;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

 class WordRepository {

    private LiveData<List<Word>> allWords;
    private WordDao wordDao;

     WordRepository(Context context){
        WordDataBase wordDataBase = WordDataBase.getInstance(context);
         wordDao = wordDataBase.getWordDao();
        allWords = wordDao.getAllWordsLive();
    }

     LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    static class InsertAsyncTak extends AsyncTask<Word,Void,Void> {
        private WordDao wordDao;

          InsertAsyncTak(WordDao wordDao){
            this.wordDao = wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }
    void insertWords(Word... words){
        new InsertAsyncTak(wordDao).execute(words);
    }

    void updateWords(Word... words){
        new UpdateAsyncTak(wordDao).execute(words);

    }

    void deleteWords(Word... words){
        new DeleteAsyncTak(wordDao).execute(words);
    }

    void clearWords(){
        new ClearAsyncTak(wordDao).execute();
    }
    static class DeleteAsyncTak extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
          DeleteAsyncTak(WordDao wordDao){
            this.wordDao = wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }
    static class UpdateAsyncTak extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
          UpdateAsyncTak(WordDao wordDao){
            this.wordDao = wordDao;
        }
        @Override
        protected Void doInBackground(Word...words) {
            wordDao.updateWords(words);
            return null;
        }
    }
    static class ClearAsyncTak extends AsyncTask<Void,Void,Void>{
        private WordDao wordDao;
          ClearAsyncTak(WordDao wordDao){
            this.wordDao = wordDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
