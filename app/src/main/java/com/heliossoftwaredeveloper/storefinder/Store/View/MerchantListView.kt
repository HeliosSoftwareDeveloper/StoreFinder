package com.heliossoftwaredeveloper.storefinder.Store.View

import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant

/**
 * Created by HELIOS.SOFTWARE.DEVELOPER on 06/06/2019.
 */
interface MerchantListView {
    fun onDestroy()
    fun onUpdateMerchantList(merchantList : List<Merchant>)
    fun updateLoaderVisibility(isVisible : Boolean)
}