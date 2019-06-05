package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantInteractor
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantInteractorImpl
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListView

/**
 * Created by HELIOS.SOFTWARE.DEVELOPER on 06/06/2019.
 */
class MerchantListPresenterImpl(merchantListView : MerchantListView)  : MerchantListPresenter{

    val mMerchantListView = merchantListView
    val merchantInteractor : MerchantInteractor =  MerchantInteractorImpl()

    override fun getMerchantList() {
        if (mMerchantListView == null) {
            return
        }

        mMerchantListView.updateLoaderVisibility(true)

        merchantInteractor.getMerchantList(object : MerchantInteractor.GetMerchantListListener{
            override fun onGetMerchantListSuccess(listMerchant: List<Merchant>) {
                if (mMerchantListView != null) {
                    mMerchantListView.updateLoaderVisibility(false)
                    mMerchantListView.onUpdateMerchantList(listMerchant)
                }
            }

            override fun onGetMerchantListError(message: String?) {
                if (mMerchantListView != null) {
                    mMerchantListView.updateLoaderVisibility(false)
                }
            }
        })
    }

    override fun onDestroy() {
        if (mMerchantListView == null) {
            return
        }
    }
}