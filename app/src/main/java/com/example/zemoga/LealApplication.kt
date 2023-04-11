package com.example.zemoga

import androidx.multidex.MultiDexApplication
import com.example.zemoga.utils.RUtil.Companion.rString
import io.realm.Realm
import io.realm.RealmConfiguration

class LealApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupRealmConfiguration()
    }

    companion object {
        private lateinit var instance: LealApplication

        fun getInstance(): LealApplication {
            return instance
        }
    }

    private fun setupRealmConfiguration() {
        val key = ByteArray(64)
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(rString(R.string.app_name))
            .schemaVersion(0)
            .encryptionKey(key)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}