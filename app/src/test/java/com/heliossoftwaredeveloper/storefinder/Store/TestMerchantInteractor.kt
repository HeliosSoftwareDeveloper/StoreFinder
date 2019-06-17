/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.API.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.RxImmediateSchedulerRule
import com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor.MerchantInteractor
import com.heliossoftwaredeveloper.storefinder.Store.Model.Interactor.MerchantInteractorImpl
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import io.reactivex.Observable
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by Ruel N. Grajo on 06/08/2019.
 *
 * Unit-test class for MerchantInteractor
 */

class TestMerchantInteractor {

    private val EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE = 16
    private val EXPECTED_EMPTY_MERCHANT_LIST_ITEM_SIZE = 0

    @Rule @JvmField val rule = MockitoJUnit.rule()!!
    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var apiService : APIService

    lateinit var interactor : MerchantInteractor

    @Before
    fun setUp() {
        interactor = MerchantInteractorImpl(apiService)
    }

    @Test
    fun testValidGetMerchantList() {
        val validMockResourcePath = javaClass.classLoader.getResource("mock_data_valid_list_merchant.json")
        val bufferedReader = BufferedReader(FileReader(validMockResourcePath.path))
        val validMockResponse = Gson().fromJson(bufferedReader, GetMerchantResponse::class.java)

        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(validMockResponse))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                //Check if the list size is correct
                assertEquals(EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE, listMerchantItems.size)
                //Check if second index type is header to check the groupMerchantByCategory() method if correct
                assertEquals(MerchantListItem.MerchantListItemType.ITEM_HEADER, listMerchantItems[0].merchantListItemType)
                //Check if second index type is child to check the groupMerchantByCategory() method if correct
                assertEquals(MerchantListItem.MerchantListItemType.ITEM_CHILD, listMerchantItems[1].merchantListItemType)
            }
            override fun onGetMerchantListError(message: String?) {
            }
        })
    }

    @Test
    fun testEmptyGetMerchantList() {
        val emptyMockResourcePath = javaClass.classLoader.getResource("mock_data_empty_list_merchant.json")
        val bufferedReader = BufferedReader(FileReader(emptyMockResourcePath.path))
        val emptyMockResponse = Gson().fromJson(bufferedReader, GetMerchantResponse::class.java)

        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(emptyMockResponse))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                //Check if the list size is correct
                assertEquals(EXPECTED_EMPTY_MERCHANT_LIST_ITEM_SIZE, listMerchantItems.size)
            }
            override fun onGetMerchantListError(message: String?) {
            }
        })
    }

    @Test
    fun testInvalidGetMerchantList() {
        val invalidMockResourcePath = javaClass.classLoader.getResource("mock_data_invalid_list_merchant.json")
        val bufferedReader = BufferedReader(FileReader(invalidMockResourcePath.path))
        val invalidMockResponse = Gson().fromJson(bufferedReader, GetMerchantResponse::class.java)

        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(invalidMockResponse))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
            }
            override fun onGetMerchantListError(message: String?) {
                //Check if the list size is correct
                assertNull(message)
            }
        })
    }
}