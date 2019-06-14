/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.API

import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Interface class for client service endpoint.
 */

interface APIService {

    @GET(Merchant.REQUEST_GET_MERCHANTS)
    fun getAllMerchant(): Observable<GetMerchantResponse>
}