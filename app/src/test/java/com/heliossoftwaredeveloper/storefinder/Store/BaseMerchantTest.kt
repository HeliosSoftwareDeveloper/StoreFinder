/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store

import com.google.gson.Gson
import com.heliossoftwaredeveloper.storefinder.API.Model.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by Ruel N. Grajo on 06/18/2019.
 *
 * Base Unit-test class for MerchantItem flow
 */

open class BaseMerchantTest {

    protected val EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE = 16
    protected val EXPECTED_EMPTY_MERCHANT_LIST_ITEM_SIZE = 0
    protected val EXPECTED_SERVICE_ERROR_MESSAGE = "An error occured. Please try again later."
    protected val PREF_NAME = "HELIOS-MERCHANT-SHARED-PREF"

    /**
     * Base class method to get valid mock merchantList response
     *
     * @return GetMerchantResponse valid mock merchant response
     * */
    fun validGetMerchantMockResponse() : GetMerchantResponse {
        return loadMockDataFromResource("mock_data_valid_get_merchant_response.json", GetMerchantResponse::class.java) as GetMerchantResponse
    }

    /**
     * Base class method to get invalid mock merchantList response
     *
     * @return GetMerchantResponse invalid mock merchant response
     * */
    fun invalidGetMerchantMockResponse() : GetMerchantResponse {
        return loadMockDataFromResource("mock_data_invalid_get_merchant_response.json", GetMerchantResponse::class.java) as GetMerchantResponse
    }

    /**
     * Base class method to get empty mock merchantList response
     *
     * @return GetMerchantResponse empty mock merchant response
     * */
    fun emptyGetMerchantMockResponse() : GetMerchantResponse {
        return loadMockDataFromResource("mock_data_empty_get_merchant_response.json", GetMerchantResponse::class.java) as GetMerchantResponse
    }

    /**
     * Base class method to get empty mock merchantList response
     *
     * @param fileName name of the mock data file
     * @param classType the expected class type of response
     *
     * @return GetMerchantResponse empty mock merchant response
     * */
    private fun loadMockDataFromResource(fileName : String, classType : Class<out Any>) : Any {
        val sourcePath = javaClass.classLoader.getResource(fileName)
        val bufferedReader = BufferedReader(FileReader(sourcePath.path))
        return Gson().fromJson(bufferedReader, classType)
    }

    /**
     * Method to build List<MerchantListItem> from service response, arranged by group category
     *
     * @param getMerchantResponse service response
     *
     * @return List<MerchantItem> list view model of merchant
     **/
    fun buildMerchantListItem(getMerchantResponse: GetMerchantResponse) : List<MerchantListItem>{
        var listMerchantItem = ArrayList<MerchantListItem>()

        for (merchantCategory in MerchantObjectMapper.mapMerchantCategoriesServiceModelToVM(getMerchantResponse.merchantCategories)) {
            listMerchantItem.add(MerchantListItem(null, merchantCategory,  MerchantListItem.MerchantListItemType.ITEM_HEADER))

            for (merchant in MerchantObjectMapper.mapMerchantsServiceModelToVM(getMerchantResponse.merchants, MerchantObjectMapper.mapMerchantBranchesServiceModelToVM(getMerchantResponse.merchantBranches))) {
                if (merchant.merchantCategory == merchantCategory.categoryId) {
                    listMerchantItem.add(MerchantListItem(merchant, null, MerchantListItem.MerchantListItemType.ITEM_CHILD))
                }
            }
        }
        return listMerchantItem
    }
}