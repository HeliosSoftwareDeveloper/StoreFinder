/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder

import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Interface class for client service endpoint.
 */

interface GetDataService {
    @GET("/HeliosSoftwareDeveloper/storagefiles/raw/e610b1ca5682577e6e3e7f0825fa41d85fabebae/storeFinder/list_merchant.json")
    fun getAllMerchant(): Observable<List<Merchant>>

    companion object {
        private val BASE_URL = "https://bitbucket.org/"
        fun create(): GetDataService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(GetDataService::class.java)
        }
    }
}