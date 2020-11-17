package com.example.roomtest

import androidx.room.*


@Dao
interface TodoDao {
    //LiveData => Todo테이블에 있는 모든 객체를 계속 관찰하고있다가 변경이 일어나면 그것을 자동으로 업데이트하도록한다.

    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo?>?

    //getAll() 은 관찰 가능한 객체가 된다.(즉 디비변경시 반응하는)
    @Insert
    fun insert(todo: Todo?)

    @Update
    fun update(todo: Todo?)

    @Delete
    fun delete(todo: Todo?)

    @Query("DELETE FROM Todo")
    fun deleteAll()
}