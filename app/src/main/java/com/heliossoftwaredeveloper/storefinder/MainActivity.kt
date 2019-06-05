/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListFragment


/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Main Activity to hold fragments and views.
 */

class MainActivity : AppCompatActivity(), MerchantListFragment.OnMerchantListFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        val transManager = fragmentManager.beginTransaction()

        val chatFragment = MerchantListFragment()
        transManager.add(R.id.layout_container, chatFragment, chatFragment.javaClass.name)
        transManager.show(chatFragment)
        transManager.commit()
    }

    override fun onMerchantClicked(merchant: Merchant) {
    }
}
