package com.example.roomtest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Todo {
    @PrimaryKey(autoGenerate = true) //autoGenerate는 알아서 id를 1씩 증가시켜준다. autoincrement와 똑같
    private var id = 0

    //@ColumnInfo(name = "first_name") ==>컬럼명 변수명과 다르게 사용 가능
    private var title: String? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    override fun toString(): String {
        return "id=> $id , title=> $title"
    }

}