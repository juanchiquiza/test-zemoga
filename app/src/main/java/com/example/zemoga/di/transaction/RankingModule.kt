package com.example.zemoga.di.transaction

import com.example.zemoga.data.database.DBHelper
import com.example.zemoga.data.interactors.TransactionInteractor
import com.example.zemoga.data.repositories.transaction.ITransactionRepository
import com.example.zemoga.data.repositories.transaction.TransactionRepository
import dagger.Module
import dagger.Provides

@Module
class TransactionModule {
    @Provides
    fun provideTransactionRepository(): ITransactionRepository {
        return TransactionRepository()
    }

    @Provides
    fun provideTransactionInteractor(): TransactionInteractor {
        return TransactionInteractor()
    }

    @Provides
    fun provideDBHelper(): DBHelper {
        return DBHelper()
    }
}