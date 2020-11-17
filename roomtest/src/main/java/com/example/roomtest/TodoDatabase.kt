package com.example.roomtest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao?

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getAppDatabase(context: Context): TodoDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java, "todo-db"
                ).build()

            }
            return INSTANCE
        }

        //디비객체제거
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}