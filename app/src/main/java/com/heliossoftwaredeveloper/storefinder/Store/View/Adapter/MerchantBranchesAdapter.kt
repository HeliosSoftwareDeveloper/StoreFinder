/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.ViewHolder.MerchantBranchViewHolder

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Adapter class for MerchantItem Branches List
 */

class MerchantBranchesAdapter(listMerchantBranches: List<MerchantItem.Branches>, merchantBranchesAdapterListener : MerchantBranchesAdapterListener) : RecyclerView.Adapter<MerchantBranchViewHolder>() {

    private var mListMerchantBranches = listMerchantBranches
    private var mMerchantListAdapterListener = merchantBranchesAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantBranchViewHolder {
        return MerchantBranchViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return mListMerchantBranches.size
    }

    override fun onBindViewHolder(holder: MerchantBranchViewHolder, position: Int) {
        holder.bind(mListMerchantBranches.get(position)!!, mMerchantListAdapterListener)
    }

    /**
     * Interface for callback of items to trigger events on fragment/activity
     */
    interface MerchantBranchesAdapterListener {
        fun onMerchantBranchItemListClicked(merchantBranch : MerchantItem.Branches)
    }
}