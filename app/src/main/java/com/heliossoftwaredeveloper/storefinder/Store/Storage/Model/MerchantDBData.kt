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
@Entity(tableName = "MerchantDBData",
        foreignKeys = [ForeignKey(entity = MerchantCategoryDBData::class,
                parentColumns = ["Id"], childColumns = ["categoryId"])])
data class MerchantDBData(@PrimaryKey(autoGenerate = false) var Id: Int,
                          var categoryId: Int,
                          var merchantName: String,
                          var merchantWebsite: String,
                          var merchantIcon: String,
                          var merchantDetails: String){
    constructor():this(0,0,"","","","")
}