/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor

import com.heliossoftwaredeveloper.storefinder.API.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.API.RetrofitClientInstance
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Interactor class to handle transactions related to merchant
 */
class MerchantInteractorImpl : MerchantInteractor {

    var disposable: Disposable? = null

    val apiService by lazy {
        RetrofitClientInstance.create()
    }

    override fun getMerchantList(getMerchantListListener: MerchantInteractor.GetMerchantListListener) {
        disposable = apiService.getAllMerchant()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            getMerchantListListener.onGetMerchantListSuccess(groupMerchantByCategory(result))
                            onDestroy()
                        },
                        {  e ->
                            getMerchantListListener.onGetMerchantListError(e.message)
                            onDestroy()
                        }
                )
    }

    fun groupMerchantByCategory(getMerchantResponse: GetMerchantResponse) : List<MerchantListItem>{
        var listMerchantItem = ArrayList<MerchantListItem>()

        for (merchantCategory in getMerchantResponse.merchantCategories) {

            listMerchantItem.add(MerchantListItem(null, merchantCategory,  MerchantListItem.MerchantListItemType.ITEM_HEADER))

            for (merchant in getMerchantResponse.merchants) {
                if (merchant.merchantCategory == merchantCategory.categoryId) {
                    listMerchantItem.add(MerchantListItem(merchant, null, MerchantListItem.MerchantListItemType.ITEM_CHILD))
                }
            }
        }
        return listMerchantItem
    }

    override fun onDestroy() {
        if (disposable!= null) {
            disposable?.dispose()
            disposable = null
        }
    }
}