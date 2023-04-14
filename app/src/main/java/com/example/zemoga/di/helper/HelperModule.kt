package com.example.zemoga.di.helper

import com.example.zemoga.data.database.DBHelper
import dagger.Module
import dagger.Provides

@Module
class HelperModule {
    @Provides
    fun provideDBHelper(): DBHelper {
        return DBHelper()
    }
}