/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Ruel N. Grajo on 06/21/2019.
 *
 * Shared Preference class helper for merchant
 */

object MerchantSharedPreferenceHelper {

    private const val PREF_NAME = "HELIOS-MERCHANT-SHARED-PREF"
    private const val KEY_LAST_DATE_SYNC = "keyHighLastDateSync"

    private const val TIME_INTERVAL_TO_SYNC = 4 * 3600 * 1000 //4 Hours

    lateinit var sharedPref: SharedPreferences

    fun initialize(context: Context){
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isSyncRequired() : Boolean {
        val availableTimeToSync = getLastDateSync() + TIME_INTERVAL_TO_SYNC
        return System.currentTimeMillis() >=  availableTimeToSync
    }

    private fun getLastDateSync() : Long {
        return sharedPref.getLong(KEY_LAST_DATE_SYNC, 0)
    }

    fun saveLastDateSync() {
        sharedPref.edit().putLong(KEY_LAST_DATE_SYNC, System.currentTimeMillis()).commit()
    }
}