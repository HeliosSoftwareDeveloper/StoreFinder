/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Storage.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantBranchDBData

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data Access Object class holder for merchant branch
 */

@Dao
interface MerchantBranchDao {
    @Query("SELECT * from MerchantBranchDBData")
    fun getAll(): List<MerchantBranchDBData>

    @Insert(onConflict = REPLACE)
    fun insert(merchantBranchData: List<MerchantBranchDBData>)
}