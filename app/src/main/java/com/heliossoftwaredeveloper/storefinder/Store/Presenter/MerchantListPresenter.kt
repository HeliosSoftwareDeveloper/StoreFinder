/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Presenter Interface class for MerchantListView
 */

interface MerchantListPresenter {
    fun getMerchantList()
    fun getCacheMerchantList() : List<MerchantListItem>
    fun onDestroy()
}