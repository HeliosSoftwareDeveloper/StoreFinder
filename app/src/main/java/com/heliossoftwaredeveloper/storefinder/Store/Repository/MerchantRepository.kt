/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Repository

import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.GetMerchantFromDBResponse
import io.reactivex.Observable

/**
 * Created by Ruel N. Grajo on 06/20/2019.
 *
 * Repository class to execute database transactions related to merchant
 */
interface MerchantRepository {

    /**
     * Interface method to save api response of getMerchantList Request
     *
     * @param getMerchantResponse callback/listener
     * */
    fun saveMerchantList(getMerchantResponse: GetMerchantResponse)

    /**
     * Interface method to save api response of getMerchantList Request
     *
     * @return Observable<GetMerchantFromDBResponse> merchant data from database cache
     * */
    fun getMerchantList() : Observable<GetMerchantFromDBResponse>

    /**
     * Interface method to cancel any ongoing request onViewDestroy
     * */
    fun onDestroy()
}