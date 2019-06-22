/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.ViewHolder

import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.MerchantListAdapter

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * ViewHolder class for MerchantListAdapter-Child
 */

class MerchantViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_merchant, parent, false)) {

    private var txtMerchantName : TextView = itemView.findViewById(R.id.txtMerchantName)
    private var txtMerchantDetails : TextView = itemView.findViewById(R.id.txtMerchantDetails)
    private var imgMechantIcon : SimpleDraweeView = itemView.findViewById(R.id.imgMechantIcon)
    private var cardViewMerchant : CardView = itemView.findViewById(R.id.cardViewMerchant)

    fun bind(merchant: MerchantItem, merchantListAdapterListener : MerchantListAdapter.MerchantListAdapterListener) {
        txtMerchantName.text = merchant.merchantName
        txtMerchantDetails.text = merchant.merchantDetails
        imgMechantIcon.setImageURI(Uri.parse(merchant.merchantIconFullPath), null)

        cardViewMerchant.setOnClickListener(View.OnClickListener {
            merchantListAdapterListener.onMerchantItemListClicked(merchant)
        })
    }
}