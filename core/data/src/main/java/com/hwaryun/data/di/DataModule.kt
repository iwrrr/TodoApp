package com.hwaryun.data.di

import com.hwaryun.data.repository.NoteRepository
import com.hwaryun.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}