/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantDetailsView

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Presenter class for MerchantListView
 */

class MerchantDetailsPresenterImpl(merchantDetailsView : MerchantDetailsView)  : MerchantDetailsPresenter{

    val mMerchantDetailsView = merchantDetailsView

    override fun getMerchantBranchMarkers(merchant: MerchantItem?) {
        if (merchant == null) {
            return
        }

        var merchantLocation : LatLng? = null //need to save the instance to move & zoom the map camera to the last branch of merchant.

        for (merchantBranches in merchant.merchantBranches) {
            merchantLocation = LatLng(merchantBranches.branchLatitude, merchantBranches.branchLongitude)

            mMerchantDetailsView.onAddMapMarker(MarkerOptions().position(merchantLocation).title(merchant.merchantName))
        }
        if (merchantLocation != null) {
            mMerchantDetailsView.onMoveMapLocationTo(merchantLocation)
        }
    }

    override fun getMerchantBranchLocation(merchantBranch: MerchantItem.Branches) {
        mMerchantDetailsView.onMoveMapLocationTo(LatLng(merchantBranch.branchLatitude, merchantBranch.branchLongitude))
    }
}