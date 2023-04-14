package com.example.zemoga.data.database

import com.example.zemoga.data.entities.PostEntity
import com.example.zemoga.di.helper.DaggerIHelperComponent
import com.example.zemoga.di.helper.HelperModule
import io.reactivex.Observable
import io.realm.Realm
import io.realm.exceptions.RealmException

class DBHelper {

    init {
        DaggerIHelperComponent.builder().helperModule(HelperModule()).build().inject(this)
    }

    fun saveTransaction(entity: PostEntity): Observable<Boolean>? {
        val realmInstance: Realm = Realm.getDefaultInstance()
        return try {
            realmInstance.executeTransaction { realm ->
                realm.insertOrUpdate(entity)
            }
            Observable.just(true)
        } catch (ex: RealmException) {
            Observable.just(false)
        } finally {
            realmInstance.close()
        }
    }

    fun getTransactions(): Observable<List<PostEntity>>? {
        return Observable.just(geTransactionsEntity())
    }

    fun deleteTransaction(id: Int): Observable<Boolean>? {
        val realmInstance: Realm = Realm.getDefaultInstance()
        return try {
            realmInstance.executeTransaction {
                it.where(PostEntity::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
            }
            Observable.just(true)
        } catch (ex: RealmException) {
            Observable.just(false)
        } finally {
            realmInstance.close()
        }
    }

    fun deleteAllTransactions(): Observable<Boolean>? {
        val realmInstance: Realm = Realm.getDefaultInstance()
        return try {
            realmInstance.executeTransaction {
                it.where(PostEntity::class.java).findAll().deleteAllFromRealm()
            }
            Observable.just(true)
        } catch (ex: RealmException) {
            Observable.just(false)
        } finally {
            realmInstance.close()
        }
    }

    private fun geTransactionsEntity(): List<PostEntity>? {
        val realm: Realm = Realm.getDefaultInstance()
        val userEntity: List<PostEntity>
        try {
            userEntity = realm.where(PostEntity::class.java).findAll()
        } catch (ex: RealmException) {
            return null
        }
        return userEntity
    }

    fun getFavoritePost(id: Int): PostEntity? {
        val realm: Realm = Realm.getDefaultInstance()
        val postEntity: PostEntity?
        try {
            postEntity = realm.where(PostEntity::class.java).equalTo("id", id).findFirst()
        } catch (ex: RealmException) {
            return null
        }
        return postEntity
    }
}
