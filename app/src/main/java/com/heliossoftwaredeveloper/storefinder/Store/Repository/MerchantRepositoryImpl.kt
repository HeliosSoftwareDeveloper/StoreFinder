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
        val saveCategory = Observable.fromCallable {AppDatabase.INSTANCE?.merchantCategoryDao()?.insert(MerchantObjectMapper
                .mapMerchantCategoryServiceModelToDB(getMerchantResponse.merchantCategories));""}.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())

        val saveMerchant = Observable.fromCallable {AppDatabase.INSTANCE?.merchantDao()?.insert(MerchantObjectMapper
                .mapMerchantServiceModelToDB(getMerchantResponse.merchants));""}.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.single())

        val saveBranch = Observable.fromCallable {AppDatabase.INSTANCE?.merchantBranchDao()?.insert(MerchantObjectMapper
                .mapMerchantBranchesServiceModelToDB(getMerchantResponse.merchantBranches));""}.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.single())

        Observable.zip(saveCategory, saveMerchant, saveBranch, Function3<String?, String?, String?, String> ({ t1, t2, t3 -> "" }))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    MerchantSharedPreferenceHelper.saveLastDateSync()
                })
    }

    override fun getMerchantList(getMerchantListListener: MerchantRepository.GetMerchantListListener) {
        val getMerchant = Observable.fromCallable {AppDatabase.INSTANCE?.merchantDao()?.getAll()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation())

        val getCategory = Observable.fromCallable {AppDatabase.INSTANCE?.merchantCategoryDao()?.getAll()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())

        val getBranch = Observable.fromCallable {AppDatabase.INSTANCE?.merchantBranchDao()?.getAll()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())

        compositeDisposable.add(Observable.zip(getMerchant, getCategory, getBranch, Function3<List<MerchantDBData>?, List<MerchantCategoryDBData>?, List<MerchantBranchDBData>?, Any> (
                { t1, t2, t3 ->   GetMerchantFromDBResponse(t1,t2,t3) }))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result -> getMerchantListListener.onGetMerchantListFinished((result as GetMerchantFromDBResponse))
                }))
    }

    override fun onDestroy() {
        if (compositeDisposable!= null) {
            compositeDisposable?.dispose()
        }
    }
}