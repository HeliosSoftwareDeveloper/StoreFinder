/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Presenter

import com.google.android.gms.maps.model.LatLng
import com.heliossoftwaredeveloper.storefinder.Store.BaseMerchantTest
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantDetailsView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Ruel N. Grajo on 06/18/2019.
 *
 * Unit-test class for MerchantListPresenter
 */

class MerchantDetailsPresenterTest : BaseMerchantTest(){

    @Mock
    private lateinit var merchantDetailsView : MerchantDetailsView

    private lateinit var presenter: MerchantDetailsPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this);
        presenter = MerchantDetailsPresenterImpl(merchantDetailsView)
    }

    @Test
    fun testValidMerchant() {
        val validMockMerchant = buildMerchantListItem(validGetMerchantMockResponse())[1].merchant
        presenter.getMerchantBranchMarkers(validMockMerchant)

        val lastValidMerchantBranch = validMockMerchant!!.merchantBranches.last()
        val merchantLocation = LatLng(lastValidMerchantBranch.branchLatitude, lastValidMerchantBranch.branchLongitude)

        //verify if the onMoveMapLocationTo has been called
        Mockito.verify(merchantDetailsView).onMoveMapLocationTo(merchantLocation)

        presenter.getMerchantBranchLocation(lastValidMerchantBranch)
    }

    @Test
    fun testNullMerchant() {
        val validMockMerchant : MerchantItem? = null
        presenter.getMerchantBranchMarkers(validMockMerchant)

        //verify that no view interaction happened
        Mockito.verifyZeroInteractions(merchantDetailsView)
    }
}
