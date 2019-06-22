/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Model

import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.BaseMerchantTest
import com.heliossoftwaredeveloper.storefinder.Store.Interactor.MerchantInteractorImpl
import com.heliossoftwaredeveloper.storefinder.Store.MerchantObjectMapper
import org.junit.Assert
import org.junit.Test

/**
 * Created by Ruel N. Grajo on 06/18/2019.
 *
 * Unit-test class for MerchantListPresenter
 */

class MerchantTest : BaseMerchantTest(){

    @Test
    fun testValidGetMerchantList() {
        val mockedMerchantListItems = buildMerchantListItem(validGetMerchantMockResponse())

        //Check the content of MerchantListItems
        Assert.assertNotNull(mockedMerchantListItems)
        Assert.assertEquals(MerchantListItem.MerchantListItemType.ITEM_HEADER, mockedMerchantListItems.get(0).merchantListItemType)
        Assert.assertEquals(MerchantListItem.MerchantListItemType.ITEM_CHILD, mockedMerchantListItems.get(1).merchantListItemType)

        val firstMerchantOnList : MerchantItem = mockedMerchantListItems[1].merchant!!
        val firstMerchantBranches : List<MerchantItem.Branches> = mockedMerchantListItems[1].merchant!!.merchantBranches
        val firstMerchantBranch : MerchantItem.Branches = firstMerchantBranches[0]
        val firstMerchantBranchLatitude : Double = firstMerchantBranch.branchLatitude
        val firstMerchantBranchLongitude : Double = firstMerchantBranch.branchLongitude

        //Check the content of the MerchantItem Model
        Assert.assertEquals(1, firstMerchantOnList.merchantID)
        Assert.assertEquals("ACE Hardware", firstMerchantOnList.merchantName)
        Assert.assertEquals(1, firstMerchantOnList.merchantCategory)
        Assert.assertEquals("https://www.acehardware.ph/", firstMerchantOnList.merchantWebsite)
        Assert.assertEquals("/images/ace_hardware_icon.png", firstMerchantOnList.merchantIcon)
        Assert.assertEquals("Ace Hardware Corporation is an American hardware retailers' cooperative based in Oak Brook, Illinois, United States. It is the world's largest hardware retail cooperative, and the largest non-grocery American retail cooperative.", firstMerchantOnList.merchantDetails)

        //Check the content of the MerchantItem Branch Model
        Assert.assertEquals(4, firstMerchantBranches.size)
        Assert.assertEquals("SM Aura Premier", firstMerchantBranch.branchName)
        Assert.assertEquals("L/GF SM Aura Premier, 26th St. cor. McKinley Parkway,, Brgy. Fort Bonifacio, Global City, Taguig, 1634 Metro Manila", firstMerchantBranch.branchAddress)
        Assert.assertEquals("10am–10pm", firstMerchantBranch.branchStoreHours)
        Assert.assertEquals("(02) 808 8276", firstMerchantBranch.branchPhoneNumber)

        //Check the Latitude and longitude value of MerchantItem Branch
        Assert.assertEquals(14.546748, firstMerchantBranchLatitude, 0.toDouble())
        Assert.assertEquals(121.0545499, firstMerchantBranchLongitude, 0.toDouble())
    }
}
