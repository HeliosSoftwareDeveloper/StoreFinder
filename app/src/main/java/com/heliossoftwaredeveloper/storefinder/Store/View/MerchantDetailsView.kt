/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Ruel N. Grajo on 06/17/2019.
 *
 * View interface for MerchantDetailsFragment
 */

interface MerchantDetailsView {
    fun onAddMapMarker(markerOptions: MarkerOptions)
    fun onMoveMapLocationTo(location : LatLng)
}