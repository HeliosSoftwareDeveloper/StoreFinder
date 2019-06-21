/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.Storage.Model

/**
 * Created by Ruel N. Grajo on 06/21/2019.
 *
 * Data class holder for GetMerchantFromDB Response
 */

data class GetMerchantFromDBResponse (val merchants : List<MerchantDBData>, val merchantCategories : List<MerchantCategoryDBData>, val merchantBranches : List<MerchantBranchDBData>)