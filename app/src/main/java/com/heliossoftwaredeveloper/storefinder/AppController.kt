/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.heliossoftwaredeveloper.storefinder.Store.MerchantSharedPreferenceHelper
import com.heliossoftwaredeveloper.storefinder.Store.Storage.AppDatabase

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Application class
 */

class AppController : Application() {

    private var mInstance: AppController? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        Fresco.initialize(this)
        MerchantSharedPreferenceHelper.initialize(this)
        AppDatabase.getAppDataBase(this)
    }
}