package com.example.room_demo1;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton单例
@Database(entities = {Word.class},version = 5,exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {
    public abstract WordDao getWordDao();

    private static WordDataBase  instance;
    static synchronized WordDataBase getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context,WordDataBase.class,"word_database").addMigrations(MIGRATION_4_5).build();
        }
        return  instance;
    }

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION_3_4= new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //思想：创建一个临时表，结构为你定义的，从旧表中复制数据过去，删除旧表，重命名新表
            database.execSQL("CREATE TABLE word_temp(id INTEGER PRIMARY KEY NOT NULL,en_word TEXT,chi_meaning TEXT)");
            database.execSQL("INSERT INTO  word_temp(id,en_word ,chi_meaning) SELECT id,en_word,chi_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME TO  word");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN hide_chinese INTEGER NOT NULL DEFAULT 0");
        }
    };
}
