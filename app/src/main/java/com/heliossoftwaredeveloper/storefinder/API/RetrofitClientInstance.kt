/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.API

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Class to hold retrofit client instance.
 */

class RetrofitClientInstance {

    companion object {
        private val BASE_URL = "https://bitbucket.org/"
        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }
}