/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store

import com.heliossoftwaredeveloper.storefinder.API.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantBranchDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantCategoryDBData
import com.heliossoftwaredeveloper.storefinder.Store.Storage.Model.MerchantDBData

/**
 * Created by Ruel N. Grajo on 06/21/2019.
 *
 * Object class to map service model, database model, and view model
 */

object MerchantObjectMapper {

    //Start of Service model to database model mapping
    /**
     * Method to map merchant service model to Database model
     *
     * @param merchants merchant list from service
     *
     * @return List<MerchantDBData> list database model of merchant
     **/
    fun mapMerchantServiceModelToDB(merchants: List<Merchant>): List<MerchantDBData> {
        return merchants.map {
            MerchantDBData(
                    Id = it.merchantId,
                    categoryId = it.categoryId,
                    merchantName = it.merchantName,
                    merchantWebsite = it.merchantWebsite,
                    merchantIcon = it.merchantIcon,
                    merchantDetails = it.merchantDetails
            )
        }
    }

    /**
     * Method to map merchantCategory service model to Database model
     *
     * @param merchantCategory merchant category list from service
     *
     * @return List<MerchantCategoryDBData> list database model of merchant category
     **/
    fun mapMerchantCategoryServiceModelToDB(merchantCategory: List<Merchant.Category>): List<MerchantCategoryDBData> {
        return merchantCategory.map {
            MerchantCategoryDBData(
                    Id = it.categoryId,
                    categoryName = it.categoryName
            )
        }
    }

    /**
     * Method to map merchantBranch service model to Database model
     *
     * @param merchantBranch merchant branch list from service
     *
     * @return List<MerchantBranchDBData> list database model of merchant branch
     **/
    fun mapMerchantBranchesServiceModelToDB(merchantBranch: List<Merchant.Branch>): List<MerchantBranchDBData> {
        return merchantBranch.map {
            MerchantBranchDBData(
                    Id = it.branchId,
                    merchantId = it.merchantId,
                    branchName = it.branchName,
                    branchAddress = it.branchAddress,
                    branchLatitude = it.branchLatitude,
                    branchLongitude = it.branchLongitude,
                    branchStoreHours = it.branchStoreHours,
                    branchPhoneNumber = it.branchPhoneNumber
            )
        }
    }
    //End of Service model to database model mapping

    //Start of Database model to view model mapping
    /**
     * Method to map merchant database model to view model
     *
     * @param merchants merchant list from database
     *
     * @return List<MerchantItem> list view model of merchant
     **/
    fun mapMerchantDBModelToVM(merchants: List<MerchantDBData>, mapBranch : Map<Int,List<MerchantItem.Branches>>): List<MerchantItem> {
        return merchants.map {
            MerchantItem(
                    merchantID = it.Id,
                    merchantCategory = it.categoryId,
                    merchantName = it.merchantName,
                    merchantWebsite = it.merchantWebsite,
                    merchantIcon = it.merchantIcon,
                    merchantDetails = it.merchantDetails,
                    merchantBranches = mapBranch.get(it.Id)!!
            )
        }
    }

    /**
     * Method to map merchant Branch database model to view model
     *
     * @param merchantBranch merchant branch list from database
     *
     * @return List<MerchantItem.Branches> list view model of merchant branch
     **/
    fun mapMerchantBranchesDBModelToVM(merchantBranch: List<MerchantBranchDBData>): Map<Int,List<MerchantItem.Branches>> {
        return merchantBranch
                .groupBy { it.merchantId }
                .mapValues { it.value.map { MerchantItem.Branches(
                        merchantID = it.merchantId,
                        branchId = it.Id,
                        branchLatitude = it.branchLatitude,
                        branchLongitude = it.branchLongitude,
                        branchName = it.branchName,
                        branchAddress = it.branchAddress,
                        branchStoreHours = it.branchStoreHours,
                        branchPhoneNumber = it.branchPhoneNumber)
                }}
    }

    /**
     * Method to map merchantCategory database model to view model
     *
     * @param merchantCategory merchant category list from database
     *
     * @return List<MerchantCategoryDBData> list view model of merchant category
     **/
    fun mapMerchantCategoryDBModelToVM(merchantCategory: List<MerchantCategoryDBData>): List<MerchantItem.Category> {
        return merchantCategory.map {
            MerchantItem.Category(
                    categoryId = it.Id,
                    categoryName = it.categoryName
            )
        }
    }
    //End of Database model to view model mapping

    //Start of Service model to view model mapping
    /**
     * Method to map merchant service model to view model
     *
     * @param merchant merchant list from service
     *
     * @return List<MerchantItem> list view model of merchant
     **/
    fun mapMerchantsServiceModelToVM(merchant: List<Merchant>, mapBranch : Map<Int,List<MerchantItem.Branches>>): List<MerchantItem> {
        return merchant.map {
            MerchantItem(
                    merchantID = it.merchantId,
                    merchantName = it.merchantName,
                    merchantCategory = it.categoryId,
                    merchantWebsite = it.merchantWebsite,
                    merchantIcon = it.merchantIcon,
                    merchantDetails = it.merchantDetails,
                    merchantBranches = mapBranch[it.merchantId]!!
            )
        }
    }

    /**
     * Method to map merchant Branch service model to view model
     *
     * @param merchantBranch merchant branch list from service
     *
     * @return List<MerchantItem.Branches> list view model of merchant branch
     **/
    fun mapMerchantBranchesServiceModelToVM(merchantBranch: List<Merchant.Branch>): Map<Int,List<MerchantItem.Branches>> {
        return merchantBranch
                .groupBy { it.merchantId }
                .mapValues { it.value.map { MerchantItem.Branches(
                        merchantID = it.merchantId,
                        branchId = it.branchId,
                        branchLatitude = it.branchLatitude,
                        branchLongitude = it.branchLongitude,
                        branchName = it.branchName,
                        branchAddress = it.branchAddress,
                        branchStoreHours = it.branchStoreHours,
                        branchPhoneNumber = it.branchPhoneNumber)
                }}
    }

    /**
     * Method to map merchant category service model to view model
     *
     * @param merchantCategory merchant category list from service
     *
     * @return List<MerchantItem.Category> list view model of merchant category
     **/
    fun mapMerchantCategoriesServiceModelToVM(merchantCategory: List<Merchant.Category>): List<MerchantItem.Category> {
        return merchantCategory.map {
            MerchantItem.Category(
                    categoryId = it.categoryId,
                    categoryName = it.categoryName
            )
        }
    }
    //End of Service model to view model mapping
}