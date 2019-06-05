package com.heliossoftwaredeveloper.storefinder.Store.Model

import android.util.Log
import com.heliossoftwaredeveloper.storefinder.GetDataService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by HELIOS.SOFTWARE.DEVELOPER on 06/06/2019.
 */
class MerchantInteractorImpl : MerchantInteractor{

    var disposable: Disposable? = null

    val apiService by lazy {
        GetDataService.create()
    }

    override fun getMerchantList(getMerchantListListener: MerchantInteractor.GetMerchantListListener) {
        disposable = apiService.getAllMerchant()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            getMerchantListListener.onGetMerchantListSuccess(result)
                        },
                        {  e ->getMerchantListListener.onGetMerchantListError(e.message)  }
                )
    }

    override fun onDestroy() {
        if (disposable!= null) {
            disposable?.dispose()
        }
    }
}