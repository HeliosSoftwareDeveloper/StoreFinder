/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Interactor

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.API.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
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

        for (merchantCategory in mapMerchantCategories(getMerchantResponse.merchantCategories)) {
            listMerchantItem.add(MerchantListItem(null, merchantCategory,  MerchantListItem.MerchantListItemType.ITEM_HEADER))

            for (merchant in mapMerchants(getMerchantResponse.merchants, mapMerchantBranches(getMerchantResponse.merchantBranches))) {
                if (merchant.merchantCategory == merchantCategory.categoryId) {
                    listMerchantItem.add(MerchantListItem(merchant, null, MerchantListItem.MerchantListItemType.ITEM_CHILD))
                }
            }
        }
        return listMerchantItem
    }

    /**
     * Method to map merchant service model to view model
     *
     * @param merchant merchant list from service
     *
     * @return List<MerchantItem> list view model of merchant
     **/
    private fun mapMerchants(merchant: List<Merchant>, mapBranch : Map<Int,List<MerchantItem.Branches>>): List<MerchantItem> {
        return merchant.map {
            MerchantItem(
                    merchantID = it.merchantId,
                    merchantName = it.merchantName,
                    merchantCategory = it.categoryId,
                    merchantWebsite = it.merchantWebsite,
                    merchantIcon = it.merchantIcon,
                    merchantDetails = it.merchantDetails,
                    merchantBranches = mapBranch[it.merchantId]!!
            )
        }
    }

    /**
     * Method to map merchant Branch service model to view model
     *
     * @param merchantBranch merchant branch list from service
     *
     * @return List<MerchantItem.Branches> list view model of merchant branch
     **/
    private fun mapMerchantBranches(merchantBranch: List<Merchant.Branch>): Map<Int,List<MerchantItem.Branches>> {
        return merchantBranch
                .groupBy { it.merchantId }
                .mapValues { it.value.map { MerchantItem.Branches(
                        merchantID = it.merchantId,
                        branchId = it.branchId,
                        branchLatitude = it.branchLatitude,
                        branchLongitude = it.branchLongitude,
                        branchName = it.branchName,
                        branchAddress = it.branchAddress,
                        branchStoreHours = it.branchStoreHours,
                        branchPhoneNumber = it.branchPhoneNumber)
                }}
    }

    /**
     * Method to map merchant category service model to view model
     *
     * @param merchantCategory merchant category list from service
     *
     * @return List<MerchantItem.Category> list view model of merchant category
     **/
    private fun mapMerchantCategories(merchantCategory: List<Merchant.Category>): List<MerchantItem.Category> {
        return merchantCategory.map {
            MerchantItem.Category(
                    categoryId = it.categoryId,
                    categoryName = it.categoryName
            )
        }
    }

    override fun onDestroy() {
        if (disposable!= null) {
            disposable?.dispose()
            disposable = null
        }
    }
}