/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Repository

import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.MerchantObjectMapper
import com.heliossoftwaredeveloper.storefinder.Store.MerchantSharedPreferenceHelper
import com.heliossoftwaredeveloper.storefinder.Store.Storage.AppDatabase
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.GetMerchantFromDBResponse
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantBranchDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantCategoryDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantDBData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function3


/**
 * Created by Ruel N. Grajo on 06/20/2019.
 *
 * Repository class to execute database transactions related to merchant
 */

class MerchantRepositoryImpl : MerchantRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun saveMerchantList(getMerchantResponse: GetMerchantResponse) {
      saveMerchantCategoryData(getMerchantResponse)
    }

    override fun getMerchantList(getMerchantListListener: MerchantRepository.GetMerchantListListener) {
        val source1 = Observable.fromCallable {AppDatabase.INSTANCE?.merchantDao()?.getAll()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation())

        val source2 = Observable.fromCallable {AppDatabase.INSTANCE?.merchantCategoryDao()?.getAll()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())

        val source3 = Observable.fromCallable {AppDatabase.INSTANCE?.merchantBranchDao()?.getAll()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())

        Observable.zip(source1, source2, source3, Function3<List<MerchantDBData>?, List<MerchantCategoryDBData>?, List<MerchantBranchDBData>?, Any> (
                {
                    t1, t2, t3 ->   GetMerchantFromDBResponse(t1,t2,t3)
                }
        ) ).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    result -> getMerchantListListener.onGetMerchantListFinished((result as GetMerchantFromDBResponse))
                })
    }

    /**
     * Method to execute the insert method of table merchant using observable
     *
     * @param getMerchantResponse response from service
     **/
    fun saveMerchantData(getMerchantResponse: GetMerchantResponse) {
        Observable.fromCallable{AppDatabase.INSTANCE?.merchantDao()?.insert(MerchantObjectMapper
                .mapMerchantServiceModelToDB(getMerchantResponse.merchants))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    saveMerchantBranchData(getMerchantResponse)
                }
                .subscribe()
    }

    /**
     * Method to execute the insert method of table merchantCategory using observable
     *
     * @param getMerchantResponse response from service
     **/
    fun saveMerchantCategoryData(getMerchantResponse: GetMerchantResponse) {
        Observable.fromCallable{AppDatabase.INSTANCE?.merchantCategoryDao()?.insert(MerchantObjectMapper
                .mapMerchantCategoryServiceModelToDB(getMerchantResponse.merchantCategories))}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    saveMerchantData(getMerchantResponse)
                }
                .subscribe()
    }

    /**
     * Method to execute the insert method of table merchantBranch using observable
     *
     * @param getMerchantResponse response from service
     **/
    fun saveMerchantBranchData(getMerchantResponse: GetMerchantResponse) {
        Observable.fromCallable{AppDatabase.INSTANCE?.merchantBranchDao()?.insert(MerchantObjectMapper
                .mapMerchantBranchesServiceModelToDB(getMerchantResponse.merchantBranches))}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    MerchantSharedPreferenceHelper.saveLastDateSync()
                }
                .subscribe()
    }

    override fun onDestroy() {
        if (compositeDisposable!= null) {
            compositeDisposable?.dispose()
        }
    }
}