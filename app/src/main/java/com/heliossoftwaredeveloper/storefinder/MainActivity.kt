/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder

import android.os.Bundle
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.View.Fragment.MerchantDetailsFragment
import com.heliossoftwaredeveloper.storefinder.Store.View.Fragment.MerchantListFragment

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Main Activity to hold fragments and views.
 */

class MainActivity : ViewNavigator(), MerchantListFragment.OnMerchantListFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nagivateTo(MerchantListFragment(), false)
    }

    override fun onMerchantClicked(merchant: Merchant) {
        nagivateTo(MerchantDetailsFragment.newInstance(merchant), true)
    }
}
