/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store

import com.google.gson.Gson
import com.heliossoftwaredeveloper.storefinder.API.GetMerchantResponse
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by Ruel N. Grajo on 06/18/2019.
 *
 * Base Unit-test class for Merchant flow
 */

open class BaseMerchantTest {

    protected val EXPECTED_VALID_MERCHANT_LIST_ITEM_SIZE = 16
    protected val EXPECTED_EMPTY_MERCHANT_LIST_ITEM_SIZE = 0
    protected val EXPECTED_SERVICE_ERROR_MESSAGE = "An error occured. Please try again later."

    /**
     * Base class method to get valid mock Merchant
     *
     * @return Merchant valid mock merchant
     * */
    fun validMerchantMockResponse() : Merchant {
        return loadMockDataFromResource("mock_data_single_merchant.json", Merchant::class.java) as Merchant
    }

    /**
     * Base class method to get valid mock merchantList response
     *
     * @return GetMerchantResponse valid mock merchant response
     * */
    fun validMerchantListMockResponse() : GetMerchantResponse {
        return loadMockDataFromResource("mock_data_valid_list_merchant.json", GetMerchantResponse::class.java) as GetMerchantResponse
    }

    /**
     * Base class method to get invalid mock merchantList response
     *
     * @return GetMerchantResponse invalid mock merchant response
     * */
    fun invalidMerchantListMockResponse() : GetMerchantResponse {
        return loadMockDataFromResource("mock_data_invalid_list_merchant.json", GetMerchantResponse::class.java) as GetMerchantResponse
    }

    /**
     * Base class method to get empty mock merchantList response
     *
     * @return GetMerchantResponse empty mock merchant response
     * */
    fun emptyMerchantListMockResponse() : GetMerchantResponse {
        return loadMockDataFromResource("mock_data_empty_list_merchant.json", GetMerchantResponse::class.java) as GetMerchantResponse
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
}