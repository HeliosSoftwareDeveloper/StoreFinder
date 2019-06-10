package com.heliossoftwaredeveloper.storefinder.Store.Model

/**
 * Created by HELIOS.SOFTWARE.DEVELOPER on 10/06/2019.
 */
data class MerchantListItem(val merchant: Merchant?, val category: MerchantCategory?, val merchantListItemType : MerchantListItemType){
    enum class MerchantListItemType{
        ITEM_HEADER, ITEM_CHILD
    }
}