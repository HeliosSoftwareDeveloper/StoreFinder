/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.heliossoftwaredeveloper.storefinder.API.APIService
import com.heliossoftwaredeveloper.storefinder.RxImmediateSchedulerRule
import com.heliossoftwaredeveloper.storefinder.Store.BaseMerchantTest
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListView
import io.reactivex.Observable
import org.junit.Assert
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
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(validMerchantListMockResponse()))

        presenter.getMerchantList()
        //verify if the updateLoaderVisibility-true has been called
        verify(merchantListView).updateLoaderVisibility(true)
        //verify if the onUpdateMerchantList has been called
        verify(merchantListView).onUpdateMerchantList(presenter.getCacheMerchantList())
        //verify if the updateLoaderVisibility-false has been called
        verify(merchantListView).updateLoaderVisibility(false)

        val firstMerchantOnList : Merchant = presenter.getCacheMerchantList()[1].merchant!!
        val firstMerchantBranches : List<Merchant.Branches> = presenter.getCacheMerchantList()[1].merchant!!.merchantBranches
        val firstMerchantBranch : Merchant.Branches = firstMerchantBranches[0]
        val firstMerchantBranchLatLng : List<Double> = firstMerchantBranch.branchLocation

        //Check the content of the Merchant Model
        Assert.assertEquals(1, firstMerchantOnList.merchantID)
        Assert.assertEquals("ACE Hardware", firstMerchantOnList.merchantName)
        Assert.assertEquals(1, firstMerchantOnList.merchantCategory)
        Assert.assertEquals("https://www.acehardware.ph/", firstMerchantOnList.merchantWebsite)
        Assert.assertEquals("/images/ace_hardware_icon.png", firstMerchantOnList.merchantIcon)
        Assert.assertEquals("Ace Hardware Corporation is an American hardware retailers' cooperative based in Oak Brook, Illinois, United States. It is the world's largest hardware retail cooperative, and the largest non-grocery American retail cooperative.", firstMerchantOnList.merchantDetails)

        //Check the content of the Merchant Branch Model
        Assert.assertEquals(4, firstMerchantBranches.size)
        Assert.assertEquals("SM Aura Premier", firstMerchantBranch.branchName)
        Assert.assertEquals("L/GF SM Aura Premier, 26th St. cor. McKinley Parkway,, Brgy. Fort Bonifacio, Global City, Taguig, 1634 Metro Manila", firstMerchantBranch.branchAddress)
        Assert.assertEquals("10am–10pm", firstMerchantBranch.branchStoreHours)
        Assert.assertEquals("(02) 808 8276", firstMerchantBranch.branchPhoneNumber)

        //Check the Latitude and longitude value of Merchant Branch
        Assert.assertEquals(2, firstMerchantBranchLatLng.size)
        Assert.assertEquals(14.546748, firstMerchantBranchLatLng[0], 0.toDouble())
        Assert.assertEquals(121.0545499, firstMerchantBranchLatLng[1], 0.toDouble())

        //Check if the list size is correct
        Assert.assertEquals(EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE, presenter.getCacheMerchantList().size)

        //call method to check if this will result to app crash
        presenter.onDestroy()
    }

    @Test
    fun testInvalidGetMerchantList() {
        Mockito.`when`(apiService.getAllMerchant()).thenReturn(Observable.just(invalidMerchantListMockResponse()))

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
