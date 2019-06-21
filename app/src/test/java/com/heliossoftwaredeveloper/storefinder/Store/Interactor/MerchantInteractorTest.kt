/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Interactor

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.RxImmediateSchedulerRule
import com.heliossoftwaredeveloper.storefinder.Store.BaseMerchantTest
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import io.reactivex.Observable
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
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

    private lateinit var interactor : MerchantInteractor

    @Before
    fun setUp() {
        interactor = MerchantInteractorImpl(apiService)
    }

    @Test
    fun testValidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(validGetMerchantMockResponse()))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                //Check if the list size is correct
                assertEquals(EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE, listMerchantItems.size)
                //Check if second index type is header to check the buildMerchantListItem() method if correct
                assertEquals(MerchantListItem.MerchantListItemType.ITEM_HEADER, listMerchantItems[0].merchantListItemType)
                //Check if second index type is child to check the buildMerchantListItem() method if correct
                assertEquals(MerchantListItem.MerchantListItemType.ITEM_CHILD, listMerchantItems[1].merchantListItemType)
            }
            override fun onGetMerchantListError(message: String?) {
            }
        })
    }

    @Test
    fun testEmptyGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(emptyGetMerchantMockResponse()))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
                //Check if the list size is empty
                assertEquals(EXPECTED_EMPTY_MERCHANT_LIST_ITEM_SIZE, listMerchantItems.size)
            }
            override fun onGetMerchantListError(message: String?) {
            }
        })
    }

    @Test
    fun testInvalidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(invalidGetMerchantMockResponse()))

        interactor.getMerchantList(object : MerchantInteractor.GetMerchantListListener {
            override fun onGetMerchantListSuccess(listMerchantItems: List<MerchantListItem>) {
            }
            override fun onGetMerchantListError(message: String?) {
                //Check if the list size is correct
                assertEquals(EXPECTED_SERVICE_ERROR_MESSAGE, message)
            }
        })
    }
}
