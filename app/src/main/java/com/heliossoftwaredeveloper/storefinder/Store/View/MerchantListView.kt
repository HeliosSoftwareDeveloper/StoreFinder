/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View

import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * View interface for MerchantListFragment
 */

interface MerchantListView {
    fun onDestroy()
    fun onUpdateMerchantList(merchantListItem : List<MerchantListItem>)
    fun updateLoaderVisibility(isVisible : Boolean)
    fun showErrorMessage(message : String)
}