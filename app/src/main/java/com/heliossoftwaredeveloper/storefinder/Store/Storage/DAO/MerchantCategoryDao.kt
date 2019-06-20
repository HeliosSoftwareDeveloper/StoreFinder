package com.heliossoftwaredeveloper.storefinder.Store.Storage.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantCategoryDBData

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data Access Object class holder for merchant category
 */

@Dao
interface MerchantCategoryDao {
    @Query("SELECT * from MerchantCategoryDBData")
    fun getAll(): List<MerchantCategoryDBData>

    @Insert(onConflict = REPLACE)
    fun insert(merchantCategoryDBData: List<MerchantCategoryDBData>)

    @Query("DELETE from MerchantCategoryDBData")
    fun deleteAll()
}