/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * ViewHolder class for MerchantListAdapter-Header
 */

class MerchantHeaderViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_header_merchant, parent, false)) {

    private var txtMerchantCategory : TextView = itemView.findViewById(R.id.txtMerchantCategory)

    fun bind(merchantCategory: MerchantItem.Category) {
        txtMerchantCategory.text = merchantCategory.categoryName
    }
}