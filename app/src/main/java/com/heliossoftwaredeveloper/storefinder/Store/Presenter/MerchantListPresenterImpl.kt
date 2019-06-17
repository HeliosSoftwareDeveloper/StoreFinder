/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.API.RetrofitClientInstance
import com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor.MerchantInteractor
import com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor.MerchantInteractorImpl
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListView

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Presenter class for MerchantListView
 */

class MerchantListPresenterImpl(merchantListView : MerchantListView)  : MerchantListPresenter{

    val mMerchantListView = merchantListView

    val apiService by lazy {
        RetrofitClientInstance.create()
    }
    val merchantInteractor : MerchantInteractor = MerchantInteractorImpl(apiService)

    var cacheMerchantList = ArrayList<MerchantListItem>()

    override fun getMerchantList() {
        if (mMerchantListView == null) {
            return
        }

        mMerchantListView.updateLoaderVisibility(true)

        merchantInteractor.getMerchantList(object : MerchantInteractor.GetMerchantListListener{
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                if (mMerchantListView != null) {
                    cacheMerchantList.clear()
                    cacheMerchantList.addAll(listMerchantItems)
                    mMerchantListView.updateLoaderVisibility(false)
                    mMerchantListView.onUpdateMerchantList(cacheMerchantList)
                }
            }

            override fun onGetMerchantListError(message: String?) {
                if (mMerchantListView != null) {
                    mMerchantListView.updateLoaderVisibility(false)
                }
            }
        })
    }

    override fun getCacheMerchantList(): List<MerchantListItem> {
        return cacheMerchantList
    }

    override fun onDestroy() {
        if (mMerchantListView == null) {
            return
        }

        if (merchantInteractor != null) {
            merchantInteractor.onDestroy()
        }
    }
}