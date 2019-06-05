/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data class holder for merchant details
 */

data class Merchant(val merchantID : Int, val merchantName : String, val merchantDetails : String, val merchantLocation : MerchantLocation) {
    data class MerchantLocation(val latitude : Double, val longitude : Double)
}
