/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Interactor

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.MerchantObjectMapper
import com.heliossoftwaredeveloper.storefinder.Store.MerchantSharedPreferenceHelper
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepository
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepositoryImpl
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.GetMerchantFromDBResponse
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
        if (MerchantSharedPreferenceHelper.isSyncRequired()) {
            disposable = apiService?.getAllMerchant()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(
                            { result ->
                                merchantRepository.saveMerchantList(result)
                                getMerchantListListener.onGetMerchantListSuccess(buildMerchantListItem(result))
                                onDestroy()
                            },
                            {  e ->
                                getMerchantListListener.onGetMerchantListError(SERVICE_ERROR_MESSAGE)
                                getMerchantListFromCache(getMerchantListListener)
                            }
                    )
        } else {
            getMerchantListFromCache(getMerchantListListener)
        }
    }

    /**
     * Method to execute transaction on repository to fetch merchant from cache database
     *
     * @param getMerchantListListener callback interface to presenter
     **/
    private fun getMerchantListFromCache(getMerchantListListener: MerchantInteractor.GetMerchantListListener) {
            merchantRepository.getMerchantList(object : MerchantRepository.GetMerchantListListener {
            override fun onGetMerchantListFinished(getMerchantFromDBResponse: GetMerchantFromDBResponse) {
                getMerchantListListener.onGetMerchantListFromCache(buildMerchantListItemFromDB(getMerchantFromDBResponse))
                onDestroy()
            }
        })
    }

    /**
     * Method to build List<MerchantListItem> from service response, arranged by group category
     *
     * @param getMerchantResponse service response
     *
     * @return List<MerchantItem> list view model of merchant
     **/
    private fun buildMerchantListItem(getMerchantResponse: GetMerchantResponse) : List<MerchantListItem>{
        var listMerchantItem = ArrayList<MerchantListItem>()

        for (merchantCategory in MerchantObjectMapper.mapMerchantCategoriesServiceModelToVM(getMerchantResponse.merchantCategories)) {
            listMerchantItem.add(MerchantListItem(null, merchantCategory,  MerchantListItem.MerchantListItemType.ITEM_HEADER))

            for (merchant in MerchantObjectMapper.mapMerchantsServiceModelToVM(getMerchantResponse.merchants, MerchantObjectMapper.mapMerchantBranchesServiceModelToVM(getMerchantResponse.merchantBranches))) {
                if (merchant.merchantCategory == merchantCategory.categoryId) {
                    listMerchantItem.add(MerchantListItem(merchant, null, MerchantListItem.MerchantListItemType.ITEM_CHILD))
                }
            }
        }
        return listMerchantItem
    }

    /**
     * Method to build List<MerchantListItem> from database cache response, arranged by group category
     *
     * @param getMerchantResponse database cache response
     *
     * @return List<MerchantItem> list view model of merchant
     **/
    private fun buildMerchantListItemFromDB(getMerchantFromDBResponse: GetMerchantFromDBResponse) : List<MerchantListItem>{
        var listMerchantItem = ArrayList<MerchantListItem>()

        for (merchantCategory in MerchantObjectMapper.mapMerchantCategoryDBModelToVM(getMerchantFromDBResponse.merchantCategories)) {
            listMerchantItem.add(MerchantListItem(null, merchantCategory,  MerchantListItem.MerchantListItemType.ITEM_HEADER))

            for (merchant in MerchantObjectMapper.mapMerchantDBModelToVM(getMerchantFromDBResponse.merchants, MerchantObjectMapper.mapMerchantBranchesDBModelToVM
            (getMerchantFromDBResponse.merchantBranches))) {
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
        merchantRepository.onDestroy()
    }
}