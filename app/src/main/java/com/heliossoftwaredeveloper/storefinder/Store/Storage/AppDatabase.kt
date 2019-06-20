/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.heliossoftwaredeveloper.storefinder.Store.Storage.DAO.MerchantBranchDao
import com.heliossoftwaredeveloper.storefinder.Store.Storage.DAO.MerchantCategoryDao
import com.heliossoftwaredeveloper.storefinder.Store.Storage.DAO.MerchantDao
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantBranchDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantCategoryDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantDBData

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Database class to cache merchants
 */

@Database(entities = [MerchantDBData::class, MerchantBranchDBData::class, MerchantCategoryDBData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun merchantDao(): MerchantDao
    abstract fun merchantBranchDao(): MerchantBranchDao
    abstract fun merchantCategoryDao(): MerchantCategoryDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "DBMerchants").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}