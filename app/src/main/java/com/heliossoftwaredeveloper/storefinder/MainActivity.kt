/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder

import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.View.Fragment.MerchantDetailsFragment
import com.heliossoftwaredeveloper.storefinder.Store.View.Fragment.MerchantListFragment
import com.heliossoftwaredeveloper.storefinder.SharedViewComponents.Constants
import com.heliossoftwaredeveloper.storefinder.SharedViewComponents.ViewNavigator

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Main Activity to hold fragments and views.
 */

class MainActivity : ViewNavigator(), MerchantListFragment.OnMerchantListFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, Constants.listUserRequiredPermission, Constants.REQUEST_CODE_USER_PERMISSION_ALL)
        }

        nagivateTo(MerchantListFragment(), false)
    }

    override fun onMerchantClicked(merchant: MerchantItem) {
        nagivateTo(MerchantDetailsFragment.newInstance(merchant), true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_CODE_USER_LOCATION_PERMISSION -> {
                //show message for future development
            }
        }
    }
}
