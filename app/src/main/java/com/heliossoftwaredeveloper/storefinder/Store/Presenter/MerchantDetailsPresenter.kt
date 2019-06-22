/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem

/**
 * Created by Ruel N. Grajo on 06/17/2019.
 *
 * Presenter Interface class for MerchantDetailsView
 */

interface MerchantDetailsPresenter {

    /**
     * Interface method to get branches from selected merchants
     * */
    fun getMerchantBranchMarkers(merchant: MerchantItem?)

    /**
     * Interface method to get branch location
     * */
    fun getMerchantBranchLocation(merchantBranch: MerchantItem.Branches)
}