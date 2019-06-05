package com.heliossoftwaredeveloper.storefinder.Store.Model

/**
 * Created by HELIOS.SOFTWARE.DEVELOPER on 06/06/2019.
 */
interface  MerchantInteractor {

    interface GetMerchantListListener {
        fun onGetMerchantListSuccess(listMerchant: List<Merchant>)
        fun onGetMerchantListError(message : String?)
    }

    fun getMerchantList(getMerchantListListener : GetMerchantListListener)
    fun onDestroy()
}