/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

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
     * Interface method to call onDestroy on interactor and cancel any pending task
     * */
    fun onDestroy()
}