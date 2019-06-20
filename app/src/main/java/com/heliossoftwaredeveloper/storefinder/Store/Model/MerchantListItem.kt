/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model

import java.io.Serializable

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Data class holder for merchant list items
 */
data class MerchantListItem(val merchant: MerchantItem?, val category: MerchantItem.Category?, val merchantListItemType : MerchantListItemType) : Serializable {
    enum class MerchantListItemType{
        ITEM_HEADER, ITEM_CHILD
    }
}