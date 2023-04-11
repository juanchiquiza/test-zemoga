package com.example.zemoga.data.interactors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.zemoga.data.dto.BranchDTO
import com.example.zemoga.data.dto.CommerceDTO
import com.example.zemoga.data.dto.TransactionDTO
import com.example.zemoga.data.entities.TransactionEntity
import com.leal.data.models.BranchModel
import com.leal.data.models.CommerceModel
import com.leal.data.models.TransactionModel
import com.example.zemoga.data.repositories.transaction.ITransactionRepository
import com.example.zemoga.di.transaction.DaggerITransactionComponent
import com.example.zemoga.di.transaction.TransactionModule
import com.example.zemoga.utils.ConnectionManager
import com.example.zemoga.utils.RUtil.Companion.context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransactionInteractor {

    @Inject
    lateinit var transactionRepository: ITransactionRepository
    private lateinit var connectionManger: ConnectionManager

    init {
        DaggerITransactionComponent.builder().transactionModule(TransactionModule()).build()
            .inject(this)
    }

    fun getTransaction(): Observable<List<TransactionModel>>? {
        connectionManger = ConnectionManager()
        return if (!connectionManger.validateConnectionWifi(context)) {
            transactionRepository.getTransactionDB()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.flatMap { dtoList ->
                    Observable.just(convertTransactionListEntityToModels(dtoList))
                }
        } else {
            transactionRepository.getTransaction()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.flatMap { dtoList ->
                    Observable.just(convertTransactionListDtoToModels(dtoList))
                }
        }
    }

    private fun convertTransactionListDtoToModels(dtoList: List<TransactionDTO>): List<TransactionModel> {
        val models = mutableListOf<TransactionModel>()
        dtoList.forEach { dto ->
            val model = convertTransactionDtoToModel(dto)
            saveTransactionEntity(convertTransactionDtoToEntity(dto))
            models.add(model)
        }
        return models
    }

    private fun convertTransactionDtoToModel(dto: TransactionDTO?): TransactionModel {
        return TransactionModel().apply {
            id = dto?.id
            userId = dto?.userId
            createdDate = dto?.createdDate
            commerce = convertCommerceDtoToModel(dto?.commerce)
            branch = convertBranchDtoToModel(dto?.branch)
        }
    }

    private fun convertCommerceDtoToModel(dto: CommerceDTO?): CommerceModel {
        return CommerceModel().apply {
            id = dto?.id
            name = dto?.name
            valueToPoints = dto?.valueToPoints
            branches = convertBranchesListDtoToModels(dto?.branches)
        }
    }

    private fun convertBranchesListDtoToModels(dtoList: List<BranchDTO>?): List<BranchModel> {
        val models = mutableListOf<BranchModel>()
        dtoList?.forEach { dto ->
            val model = convertBranchDtoToModel(dto)
            models.add(model)
        }
        return models
    }

    private fun convertBranchDtoToModel(dto: BranchDTO?): BranchModel {
        return BranchModel().apply {
            id = dto?.id
            name = dto?.name
        }
    }

    private fun convertTransactionDtoToEntity(userDto: TransactionDTO): TransactionEntity {
        val gson = Gson()
        val jsonString = gson.toJson(userDto)
        return TransactionEntity().apply {
            id = userDto.id
            transactionObj = jsonString
        }
    }

    private fun saveTransactionEntity(entity: TransactionEntity?): Observable<TransactionEntity> {
        entity?.let { transactionRepository.saveTransaction(it) }
        return Observable.just(entity)
    }

    private fun convertTransactionListEntityToModels(dtoList: List<TransactionEntity>): List<TransactionModel> {
        val models = mutableListOf<TransactionModel>()
        dtoList.forEach { dto ->
            val model =
                convertTransactionDtoToModel(convertTransactionEntityToModel(dto.transactionObj))
            models.add(model)
        }
        return models
    }

    private fun convertTransactionEntityToModel(json: String?): TransactionDTO {
        val gson = Gson()
        val transaction = object : TypeToken<TransactionDTO>() {}.type
        return gson.fromJson(json, transaction)
    }

    fun deleteTransaction(id: Int): Observable<Boolean>? {
        return transactionRepository.deleteTransaction(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { it ->
                Observable.just(it)
            }
    }

    fun deleteAllTransaction(): Observable<Boolean>? {
        return transactionRepository.deleteAllTransaction()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { it ->
                Observable.just(it)
            }
    }
}