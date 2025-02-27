package com.example.myapplication.data.database

import io.realm.Realm
import io.realm.RealmObject

open class RealmDaoBase<T : RealmObject> (var db : Realm) {
    fun copyOrUpdate( entry : T ) {
        db.executeTransaction {
            it.copyToRealmOrUpdate(entry)
        }
    }
    fun copyOrUpdate ( entry : List<T> ) {
        db.executeTransaction {
            it.copyToRealmOrUpdate(entry)
        }
    }
}