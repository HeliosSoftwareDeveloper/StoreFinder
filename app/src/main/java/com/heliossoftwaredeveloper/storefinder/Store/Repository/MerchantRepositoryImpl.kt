/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Repository

import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.API.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Storage.AppDatabase
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantBranchDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantCategoryDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantDBData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Repository class to execute database transactions related to merchant
 */
class MerchantRepositoryImpl : MerchantRepository {

    val compositeDisposable = CompositeDisposable()

    override fun saveMerchantList(getMerchantResponse: GetMerchantResponse) {
        saveMerchantCategoryData(getMerchantResponse)
    }

    /**
     * Method to execute the insert method of table merchant using observable
     *
     * @param getMerchantResponse response from service
     **/
    fun saveMerchantData(getMerchantResponse: GetMerchantResponse) {
        compositeDisposable.add(Observable.fromCallable{AppDatabase.INSTANCE?.merchantDao()?.insert(mapMerchant(getMerchantResponse.merchants))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    saveMerchantBranchData(getMerchantResponse)
                }
                .subscribe())
    }

    /**
     * Method to execute the insert method of table merchantCategory using observable
     *
     * @param getMerchantResponse response from service
     **/
    fun saveMerchantCategoryData(getMerchantResponse: GetMerchantResponse) {
        compositeDisposable.add(Observable.fromCallable{AppDatabase.INSTANCE?.merchantCategoryDao()?.insert(mapMerchantCategory(getMerchantResponse.merchantCategories))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    saveMerchantData(getMerchantResponse)
                }
                .subscribe())
    }

    /**
     * Method to execute the insert method of table merchantBranch using observable
     *
     * @param getMerchantResponse response from service
     **/
    fun saveMerchantBranchData(getMerchantResponse: GetMerchantResponse) {
        compositeDisposable.add(Observable.fromCallable{AppDatabase.INSTANCE?.merchantBranchDao()?.insert(mapMerchantBranches(getMerchantResponse.merchantBranches))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    /**
     * Method to map merchant service model to Database model
     *
     * @param merchants merchant list from service
     *
     * @return List<MerchantDBData> list database model of merchant
     **/
    private fun mapMerchant(merchants: List<Merchant>): List<MerchantDBData> {
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

    /**
     * Method to map merchantCategory service model to Database model
     *
     * @param merchantCategory merchant category list from service
     *
     * @return List<MerchantCategoryDBData> list database model of merchant category
     **/
    private fun mapMerchantCategory(merchantCategory: List<Merchant.Category>): List<MerchantCategoryDBData> {
        return merchantCategory.map {
            MerchantCategoryDBData(
                    Id = it.categoryId,
                    categoryName = it.categoryName
            )
        }
    }

    /**
     * Method to map merchantBranch service model to Database model
     *
     * @param merchantBranch merchant branch list from service
     *
     * @return List<MerchantBranchDBData> list database model of merchant branch
     **/
    private fun mapMerchantBranches(merchantBranch: List<Merchant.Branch>): List<MerchantBranchDBData> {
        return merchantBranch.map {
            MerchantBranchDBData(
                    Id = it.branchId,
                    merchantId = it.merchantId,
                    branchName = it.branchName,
                    branchAddress = it.branchAddress,
                    branchLatitude = it.branchLatitude,
                    branchLongitude = it.branchLongitude,
                    branchStoreHours = it.branchStoreHours,
                    branchPhoneNumber = it.branchPhoneNumber
            )
        }
    }

    override fun onDestroy() {
        if (compositeDisposable!= null) {
            compositeDisposable?.dispose()
        }
    }
}