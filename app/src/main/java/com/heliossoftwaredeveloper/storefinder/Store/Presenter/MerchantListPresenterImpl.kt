/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.Store.Interactor.MerchantInteractor
import com.heliossoftwaredeveloper.storefinder.Store.Interactor.MerchantInteractorImpl
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepository
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListView

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Presenter class for MerchantListView
 */

class MerchantListPresenterImpl(merchantListView : MerchantListView, apiService : APIService, merchantRepository : MerchantRepository)  : MerchantListPresenter{

    val mMerchantListView = merchantListView

    val merchantInteractor : MerchantInteractor = MerchantInteractorImpl(apiService, merchantRepository)

    override fun getMerchantList() {
        if (mMerchantListView == null) {
            return
        }
        mMerchantListView.updateLoaderVisibility(true)

        merchantInteractor.getMerchantList(object : MerchantInteractor.GetMerchantListListener{
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                if (mMerchantListView != null) {
                    mMerchantListView.updateLoaderVisibility(false)
                    mMerchantListView.onUpdateMerchantList(listMerchantItems)
                }
            }

            override fun onGetMerchantListError(message: String?) {
                if (mMerchantListView != null) {
                    mMerchantListView.showErrorMessage(message!!)
                }
            }

            override fun onGetMerchantListFromCache(listMerchantItems: List<MerchantListItem>) {
                if (mMerchantListView != null) {
                    mMerchantListView.updateLoaderVisibility(false)
                    mMerchantListView.onUpdateMerchantList(listMerchantItems)
                }
            }
        })
    }

    override fun onDestroy() {
        if (mMerchantListView == null) {
            return
        }
        merchantInteractor?.onDestroy()
    }
}