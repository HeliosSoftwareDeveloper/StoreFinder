/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.API.Model

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data class holder for merchant details from the service
 */

data class Merchant(val merchantName : String, val merchantWebsite : String, val merchantIcon : String, val merchantDetails : String, val merchantId : Int, val categoryId : Int) {

    data class Category (val categoryName : String, val categoryId : Int)

    data class Branch (val branchId : Int, val branchName : String, val branchAddress : String, val branchLatitude : Double, val branchLongitude : Double, val branchStoreHours : String, val branchPhoneNumber : String, val merchantId : Int)
}