/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantDetailsView

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Presenter class for MerchantListView
 */

class MerchantDetailsPresenterImpl(merchantDetailsView : MerchantDetailsView)  : MerchantDetailsPresenter{

    val mMerchantDetailsView = merchantDetailsView

    override fun getMerchantBranchMarkers(merchant: Merchant) {

        var merchantLocation : LatLng? = null //need to save the instance to move & zoom the map camera to the last branch of merchant.

        for (merchantBranches in merchant.merchantBranches) {
            val brachLocation = merchantBranches.branchLocation
            merchantLocation = LatLng(brachLocation.first(), brachLocation.get(brachLocation.lastIndex))

            mMerchantDetailsView.onAddMapMarker(MarkerOptions().position(merchantLocation).title(merchant.merchantName))
        }
        if (merchantLocation != null) {
            mMerchantDetailsView.onMoveMapLocationTo(merchantLocation)
        }
    }

    override fun getMerchantBranchLocation(merchantBranch: Merchant.Branches) {
        val brachLocation = merchantBranch.branchLocation
        mMerchantDetailsView.onMoveMapLocationTo(LatLng(brachLocation.first(), brachLocation.get(brachLocation.lastIndex)))
    }
}