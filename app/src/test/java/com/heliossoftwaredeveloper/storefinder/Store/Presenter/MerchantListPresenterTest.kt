/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.RxImmediateSchedulerRule
import com.heliossoftwaredeveloper.storefinder.Store.BaseMerchantTest
import com.heliossoftwaredeveloper.storefinder.Store.Interactor.MerchantInteractorImpl
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListView
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit

/**
 * Created by Ruel N. Grajo on 06/18/2019.
 *
 * Unit-test class for MerchantListPresenter
 */

class MerchantListPresenterTest : BaseMerchantTest(){

    @Rule @JvmField val rule = MockitoJUnit.rule()!!
    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock private lateinit var apiService : APIService
    @Mock private lateinit var merchantListView : MerchantListView

    private lateinit var presenter: MerchantListPresenter

    @Before
    fun setup() {
        presenter = MerchantListPresenterImpl(merchantListView, apiService)
    }

    @Test
    fun testValidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(validGetMerchantMockResponse()))

        val mockedMerchantListItems = MerchantInteractorImpl(apiService).buildMerchantListItem(validGetMerchantMockResponse())

        presenter.getMerchantList()
        //verify if the updateLoaderVisibility-true has been called
        verify(merchantListView).updateLoaderVisibility(true)
        //verify if the onUpdateMerchantList has been called
        verify(merchantListView).onUpdateMerchantList(mockedMerchantListItems)
        //verify if the updateLoaderVisibility-false has been called
        verify(merchantListView).updateLoaderVisibility(false)

        //call method to check if this will result to app crash
        presenter.onDestroy()
    }

    @Test
    fun testInvalidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(invalidGetMerchantMockResponse()))

        presenter.getMerchantList()
        //verify if the updateLoaderVisibility-true has been called
        verify(merchantListView).updateLoaderVisibility(true)
        //verify if the updateLoaderVisibility-false has been called
        verify(merchantListView).updateLoaderVisibility(false)
        //verify if the showErrorMessage has been called
        verify(merchantListView).showErrorMessage(EXPECTED_SERVICE_ERROR_MESSAGE)

        //call method to check if this will result to app crash
        presenter.onDestroy()
    }
}
