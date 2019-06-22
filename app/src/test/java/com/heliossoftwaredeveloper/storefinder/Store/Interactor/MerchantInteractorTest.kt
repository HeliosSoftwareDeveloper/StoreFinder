/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Interactor

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.RxImmediateSchedulerRule
import com.heliossoftwaredeveloper.storefinder.Store.BaseMerchantTest
import com.heliossoftwaredeveloper.storefinder.Store.MerchantSharedPreferenceHelper
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepository
import com.heliossoftwaredeveloper.storefinder.Store.Repository.MerchantRepositoryImpl
import com.heliossoftwaredeveloper.storefinder.Store.Storage.AppDatabase
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.GetMerchantFromDBResponse
import io.reactivex.Observable
import org.junit.*
import org.junit.Assert.*
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Unit-test class for MerchantInteractor
 */

class MerchantInteractorTest : BaseMerchantTest(){

    @Rule @JvmField val rule = MockitoJUnit.rule()!!
    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock private lateinit var apiService : APIService
    @Mock private lateinit var merchantRepository: MerchantRepository
    @Mock private lateinit var context : Context
    @Mock private lateinit var sharePref : SharedPreferences
    @Mock private lateinit var editor: SharedPreferences.Editor

    private lateinit var interactor : MerchantInteractor

    @Before
    fun setUp() {
        `when`(context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)).thenReturn(sharePref)
        Mockito.`when`(sharePref.edit()).thenReturn(editor)
        `when`(editor.putLong(any(String::class.java), any(Long::class.java))).thenReturn(editor)

        MerchantSharedPreferenceHelper.initialize(context)
        interactor = MerchantInteractorImpl(apiService, merchantRepository)
    }

    @Test
    fun testValidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(validGetMerchantMockResponse()))
        Mockito.`when`(merchantRepository.getMerchantList()).thenReturn(Observable.just(GetMerchantFromDBResponse(ArrayList(),ArrayList(),ArrayList())))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener{
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {//Check if the list size is correct
                assertEquals(EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE, listMerchantItems.size)
                //Check if second index type is header to check the buildMerchantListItem() method if correct
                assertEquals(MerchantListItem.MerchantListItemType.ITEM_HEADER, listMerchantItems[0].merchantListItemType)
                //Check if second index type is child to check the buildMerchantListItem() method if correct
                assertEquals(MerchantListItem.MerchantListItemType.ITEM_CHILD, listMerchantItems[1].merchantListItemType)
            }

            override fun onGetMerchantListFromCache(listMerchantItems: List<MerchantListItem>) {
            }

            override fun onGetMerchantListError(message: String?) {
            }
        })
    }

    @Test
    fun testEmptyGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(emptyGetMerchantMockResponse()))
        Mockito.`when`(merchantRepository.getMerchantList()).thenReturn(Observable.just(GetMerchantFromDBResponse(ArrayList(),ArrayList(),ArrayList())))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                //Check if the list size is empty
                assertEquals(EXPECTED_EMPTY_MERCHANT_LIST_ITEM_SIZE, listMerchantItems.size)
            }

            override fun onGetMerchantListFromCache(listMerchantItems: List<MerchantListItem>) {
            }

            override fun onGetMerchantListError(message: String?) {
            }
        })
    }

    @Test
    fun testInvalidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(invalidGetMerchantMockResponse()))
        Mockito.`when`(merchantRepository.getMerchantList()).thenReturn(Observable.just(GetMerchantFromDBResponse(ArrayList(),ArrayList(),ArrayList())))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
            }

            override fun onGetMerchantListFromCache(listMerchantItems: List<MerchantListItem>) {
            }

            override fun onGetMerchantListError(message: String?) {
                //Check if the list size is correct
                assertEquals(EXPECTED_SERVICE_ERROR_MESSAGE, message)
            }
        })
    }
}
