/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model

import java.io.Serializable

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data class holder for merchant details
 */

data class Merchant(val merchantID : Int, val merchantName : String, val merchantCategory : Int, val merchantWebsite : String, val merchantIcon: String, val merchantDetails : String, val merchantBranches : List<Branches>) : Serializable {

    data class Branches(val branchLocation : List<Double>, val branchName : String, val branchAddress : String, val branchStoreHours : String, val branchPhoneNumber : String) : Serializable

    companion object {
       const val IMAGE_PATH = "https://bitbucket.org/HeliosSoftwareDeveloper/storagefiles/raw/b516a0152842c9b48eb0254dfab72cad3aaf02a8/storeFinder%s"
       const val REQUEST_GET_MERCHANTS = "/HeliosSoftwareDeveloper/storagefiles/raw/ededcd6a595c5e900e30f78be3e2bce512323846/storeFinder/list_merchant.json"
    }
}
