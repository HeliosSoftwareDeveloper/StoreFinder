/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor

import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Interactor class to handle transactions related to merchant
 */
interface  MerchantInteractor {

    interface GetMerchantListListener {
        fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>)
        fun onGetMerchantListError(message : String?)
    }

    fun getMerchantList(getMerchantListListener : GetMerchantListListener)
    fun onDestroy()
}