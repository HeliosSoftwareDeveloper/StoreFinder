/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Repository

import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Interactor class to handle transactions related to merchant
 */
interface MerchantRepository {

    /**
     * Interface method to save api response of getMerchantList Request
     *
     * @param getMerchantResponse callback/listener
     * */
    fun saveMerchantList(getMerchantResponse: GetMerchantResponse)

    /**
     * Interface method to cancel any ongoing request onViewDestroy
     * */
    fun onDestroy()
}