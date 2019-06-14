/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Presenter Interface class for MerchantListView
 */

interface MerchantListPresenter {

    /**
     * Interface method to call getMerchantList on the interactor
     * */
    fun getMerchantList()

    /**
     * Interface method to get merchantList from the cache variable
     *
     * @return List<MerchantListItem> the cached merchantList from variable
     * */
    fun getCacheMerchantList() : List<MerchantListItem>

    /**
     * Interface method to call onDestroy on interactor and cancel any pending task
     * */
    fun onDestroy()
}