package com.example.myapplication.data.database

import android.content.Context
import io.realm.Realm

class RealmProvider{
    companion object {
        fun getInstanceByCurrentUser(context : Context) : Realm {
            return RealmConfigure(context, "DATABASE").init()
        }
    }
}