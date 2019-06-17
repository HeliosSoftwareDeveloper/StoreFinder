/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor

import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Interactor class to handle transactions related to merchant
 */
interface MerchantInteractor {

    /**
     * Interface callback for getMerchantList from services
     * */
    interface GetMerchantListListener {
        fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>)
        fun onGetMerchantListError(message : String?)
    }

    /**
     * Interface method to call getMerchantList Request
     *
     * @param getMerchantListListener callback/listener
     * */
    fun getMerchantList(getMerchantListListener : GetMerchantListListener)

    /**
     * Interface method to cancel any ongoing request onViewDestroy
     * */
    fun onDestroy()
}