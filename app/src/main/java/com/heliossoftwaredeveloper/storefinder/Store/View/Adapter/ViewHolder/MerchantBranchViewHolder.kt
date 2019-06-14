/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.ViewHolder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.MerchantBranchesAdapter

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * ViewHolder class for MerchantListAdapter-Header
 */

class MerchantBranchViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_merchant_branch, parent, false)) {

    private var txtMerchantBranchName : TextView = itemView.findViewById(R.id.txtMerchantBranchName)
    private var txtMerchantBranchAddress : TextView = itemView.findViewById(R.id.txtMerchantBranchAddress)
    private var txtMerchantBranchStoreHoursAndNumber : TextView = itemView.findViewById(R.id.txtMerchantBranchStoreHoursAndNumber)
    private var cardViewMerchantBranch : CardView = itemView.findViewById(R.id.cardViewMerchantBranch)

    fun bind(merchantBranches: Merchant.Branches, merchantBranchesAdapterListener : MerchantBranchesAdapter.MerchantBranchesAdapterListener) {
        txtMerchantBranchName.text = merchantBranches.branchName
        txtMerchantBranchAddress.text = merchantBranches.branchAddress
        txtMerchantBranchStoreHoursAndNumber.text = "${merchantBranches.branchStoreHours} - ${merchantBranches.branchPhoneNumber}"

        cardViewMerchantBranch.setOnClickListener(View.OnClickListener {
            merchantBranchesAdapterListener.onMerchantBranchItemListClicked(merchantBranches)
        })
    }
}