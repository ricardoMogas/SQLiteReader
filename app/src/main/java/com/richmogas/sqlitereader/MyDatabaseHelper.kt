package com.richmogas.sqlitereader
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory

class MyDatabaseHelper(context: Context) {
    private val config = SupportSQLiteOpenHelper.Configuration.builder(context)
        .name("test.db")
        .callback(object : SupportSQLiteOpenHelper.Callback(1) {
            override fun onCreate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY, nombre TEXT)")
            }

            override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
                // Lógica para actualizar BD si es necesario
            }
        })
        .build()

    private val helper: SupportSQLiteOpenHelper = FrameworkSQLiteOpenHelperFactory().create(config)

    val writableDatabase: SupportSQLiteDatabase
        get() = helper.writableDatabase
}