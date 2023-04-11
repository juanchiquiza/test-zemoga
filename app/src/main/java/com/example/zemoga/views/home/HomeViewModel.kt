package com.example.zemoga.views.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.zemoga.data.interactors.TransactionInteractor
import com.example.zemoga.data.models.ApiErrorModel
import com.leal.data.models.TransactionModel
import com.example.zemoga.di.transaction.DaggerITransactionComponent
import com.example.zemoga.di.transaction.TransactionModule
import com.example.zemoga.livedata.SingleLiveEvent
import com.example.zemoga.utils.network.ApiError
import javax.inject.Inject

@SuppressLint("CheckResult, DefaultLocale")
class HomeViewModel : ViewModel() {

    @Inject
    lateinit var transactionInteractor: TransactionInteractor

    init {
        DaggerITransactionComponent.builder().transactionModule(TransactionModule()).build()
            .inject(this)
    }

    var singleLiveEvent: SingleLiveEvent<ViewEvent> = SingleLiveEvent()

    sealed class ViewEvent {
        class ResponseTransactions(val transaction: List<TransactionModel>) : ViewEvent()
        class ResponseDeleteTransaction(val result: Boolean) : ViewEvent()
        class ResponseDeleteAllTransactions(val result: Boolean) : ViewEvent()
        class ResponseError(val apiError: ApiErrorModel) : ViewEvent()
    }

    fun getTransactions() {
        transactionInteractor.getTransaction()?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponseTransactions(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }

    fun deleteTransaction(id: Int) {
        transactionInteractor.deleteTransaction(id)?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponseDeleteTransaction(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }

    fun deleteAllTransaction() {
        transactionInteractor.deleteAllTransaction()?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponseDeleteAllTransactions(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }
}