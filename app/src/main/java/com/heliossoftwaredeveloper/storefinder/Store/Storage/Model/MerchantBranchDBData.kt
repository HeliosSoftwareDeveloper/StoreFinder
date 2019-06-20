/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Storage.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Ruel N. Grajo on 06/19/2019.
 *
 * Database Table Model class for merchant
 */
@Entity(tableName = "MerchantBranchDBData",
        foreignKeys = [ForeignKey(entity = MerchantDBData::class,
                parentColumns = ["Id"], childColumns = ["merchantId"])])
data class MerchantBranchDBData(@PrimaryKey(autoGenerate = false) var Id: Int,
                                var merchantId: Int,
                                var branchName: String,
                                var branchAddress: String,
                                var branchLatitude: Double,
                                var branchLongitude: Double,
                                var branchStoreHours: String,
                                var branchPhoneNumber: String){
    constructor():this(0,0,"","",0.0,0.0, "", "")
}