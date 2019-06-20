/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Interactor

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepository
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Interactor class to handle transactions related to merchant
 */
class MerchantInteractorImpl(private val apiService : APIService? = null) : MerchantInteractor {

    private var disposable: Disposable? = null
    private val SERVICE_ERROR_MESSAGE = "An error occured. Please try again later."
    private val merchantRepository : MerchantRepository = MerchantRepositoryImpl()

    override fun getMerchantList(getMerchantListListener: MerchantInteractor.GetMerchantListListener) {
        disposable = apiService?.getAllMerchant()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { result ->
                            merchantRepository.saveMerchantList(result)
                            getMerchantListListener.onGetMerchantListSuccess(groupMerchantByCategory(result))
                            onDestroy()
                        },
                        {  e ->
                            getMerchantListListener.onGetMerchantListError(SERVICE_ERROR_MESSAGE)
                            onDestroy()
                        }
                )
    }

    fun groupMerchantByCategory(getMerchantResponse: GetMerchantResponse) : List<MerchantListItem>{
        var listMerchantItem = ArrayList<MerchantListItem>()
       /* for (merchantCategory in getMerchantResponse.merchantCategories) {

            listMerchantItem.add(MerchantListItem(null, merchantCategory,  MerchantListItem.MerchantListItemType.ITEM_HEADER))

            for (merchant in getMerchantResponse.merchants) {
                if (merchant.merchantCategory == merchantCategory.categoryId) {
                    listMerchantItem.add(MerchantListItem(merchant, null, MerchantListItem.MerchantListItemType.ITEM_CHILD))
                }
            }
        }*/
        return listMerchantItem
    }


    override fun onDestroy() {
        if (disposable!= null) {
            disposable?.dispose()
            disposable = null
        }
    }
}