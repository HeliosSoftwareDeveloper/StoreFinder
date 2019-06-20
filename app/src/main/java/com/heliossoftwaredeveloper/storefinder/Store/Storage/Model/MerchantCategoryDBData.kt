/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Storage.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Ruel N. Grajo on 06/19/2019.
 *
 * Database Table Model class for merchant
 */
@Entity(tableName = "MerchantCategoryDBData")
data class MerchantCategoryDBData(@PrimaryKey(autoGenerate = false) var Id: Int,
                                  var categoryName: String){
    constructor():this(0,"")
}