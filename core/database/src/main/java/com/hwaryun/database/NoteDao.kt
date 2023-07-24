package com.hwaryun.database

import androidx.room.Dao
import androidx.room.Query
import com.hwaryun.database.model.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<NoteEntity>
}