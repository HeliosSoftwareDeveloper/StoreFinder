/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.API

import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantCategory

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data class holder for GetMerchantAPI Response
 */

data class GetMerchantResponse (val merchants : List<Merchant>, val merchantCategories : List<MerchantCategory>)