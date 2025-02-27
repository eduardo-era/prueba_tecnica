package com.example.myapplication.data.database

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmConfigure(var context: Context, private var useId: String) {
    fun init(): Realm {
        Realm.init(context)
        return Realm.getInstance(createConfigurationRealm(generateDatabaseName()))
    }

    private fun generateDatabaseName(): String {
        return "${useId}.realm"
    }

    private fun createConfigurationRealm(databaseName: String): RealmConfiguration {
        return RealmConfiguration.Builder()
            .modules(RealmModule())
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .name(databaseName)
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}