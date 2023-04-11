package com.example.zemoga.di.transaction

import com.example.zemoga.data.interactors.TransactionInteractor
import com.example.zemoga.data.repositories.transaction.TransactionRepository
import com.example.zemoga.views.home.HomeViewModel
import dagger.Component

@Component(modules = [TransactionModule::class])
interface ITransactionComponent {

    fun inject(transactionRepository: TransactionRepository)
    fun inject(transactionInteractor: TransactionInteractor)
    fun inject(homeViewModel: HomeViewModel)
}