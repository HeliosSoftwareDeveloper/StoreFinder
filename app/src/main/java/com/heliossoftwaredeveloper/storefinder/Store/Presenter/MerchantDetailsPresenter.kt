/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant

/**
 * Created by Ruel N. Grajo on 06/17/2019.
 *
 * Presenter Interface class for MerchantDetailsView
 */

interface MerchantDetailsPresenter {

    /**
     * Interface method to get branches from selected merchants
     * */
    fun getMerchantBranchMarkers(merchant: Merchant?)

    /**
     * Interface method to get branch location
     * */
    fun getMerchantBranchLocation(merchantBranch: Merchant.Branches)
}