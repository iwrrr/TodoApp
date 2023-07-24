package com.hwaryun.data.repository

import com.hwaryun.common.DataResult
import com.hwaryun.common.ext.proceed
import com.hwaryun.database.NoteDao
import com.hwaryun.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NoteRepository {

    fun getNotes(): Flow<DataResult<List<NoteEntity>>>
    fun getNote(id: Int): Flow<DataResult<NoteEntity>>
    fun upsertNote(title: String, desc: String, dueDate: Long): Flow<DataResult<Unit>>
}

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<DataResult<List<NoteEntity>>> = flow {
        emit(proceed { noteDao.getNotes() })
    }

    override fun getNote(id: Int): Flow<DataResult<NoteEntity>> = flow {
        emit(proceed { noteDao.getNote(id) })
    }

    override fun upsertNote(title: String, desc: String, dueDate: Long): Flow<DataResult<Unit>> = flow {
        val noteEntity = NoteEntity(
            title = title,
            desc = desc,
            dueDate = dueDate
        )

        emit(proceed { noteDao.upsertNote(noteEntity) })
    }
}