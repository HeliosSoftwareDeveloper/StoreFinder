/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Interactor

import android.util.Log
import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.API.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.Storage.AppDatabase
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantCategoryDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantDBData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

    override fun getMerchantList(getMerchantListListener: MerchantInteractor.GetMerchantListListener) {
        disposable = apiService?.getAllMerchant()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { result ->
                            saveMerchantCategoryData(result)
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

    private var db: AppDatabase? = null

    val compositeDisposable = CompositeDisposable()

    fun saveMerchantData(merchants: List<Merchant>) {

        Log.e("size list", "-"+mapMerchant(merchants).size)
        compositeDisposable.add(Observable.fromCallable{AppDatabase.INSTANCE?.merchantDao()?.insert(mapMerchant(merchants))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("result", "a "+result)
                        },
                        {  e ->
                            Log.e("error", "a "+e)
                        }
                ))
    }

    fun saveMerchantCategoryData(getMerchantResponse: GetMerchantResponse) {

        compositeDisposable.add(Observable.fromCallable{AppDatabase.INSTANCE?.merchantCategoryDao()?.insert(mapMerchantCategory(getMerchantResponse
                .merchantCategories))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            saveMerchantData(getMerchantResponse.merchants)
                            this.disposable?.dispose()
                            Log.e("result", "a "+result)
                        }
                ))
    }

    fun mapMerchant(merchants: List<Merchant>): List<MerchantDBData> {
        return merchants.map {
            MerchantDBData(
                    Id = it.merchantId,
                    categoryId = it.categoryId,
                    merchantName = it.merchantName,
                    merchantWebsite = it.merchantWebsite,
                    merchantIcon = it.merchantIcon,
                    merchantDetails = it.merchantDetails
            )
        }
    }

    fun mapMerchantCategory(merchantCategory: List<Merchant.Category>): List<MerchantCategoryDBData> {
        return merchantCategory.map {
            MerchantCategoryDBData(
                    Id = it.categoryId,
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