/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.API

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Interface class for client service endpoint.
 */

interface APIService {

    @GET("/HeliosSoftwareDeveloper/storagefiles/raw/ededcd6a595c5e900e30f78be3e2bce512323846/storeFinder/list_merchant.json")
    fun getAllMerchant(): Observable<GetMerchantResponse>
}