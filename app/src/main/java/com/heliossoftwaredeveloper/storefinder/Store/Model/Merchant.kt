/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data class holder for merchant details
 */

data class Merchant(val merchantID : Int, val merchantName : String, val merchantCategory : Int, val merchantWebsite : String,
                    val merchantIcon: String, val merchantDetails : String, val merchantBranches : List<Branches>) {
    data class Branches(val branchLocation : List<Double>, val branchName : String, val branchAddress : String, val branchStoreHours : String, val branchPhoneNumber : String)
}
