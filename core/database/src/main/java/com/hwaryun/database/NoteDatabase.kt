package com.hwaryun.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hwaryun.database.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
}