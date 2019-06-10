/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant

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

    fun bind(merchant: Merchant) {
        txtMerchantName.text = merchant.merchantName
        txtMerchantDetails.text = merchant.merchantDetails
        imgMechantIcon.setImageURI(Uri.parse("https://bitbucket.org/HeliosSoftwareDeveloper/storagefiles/raw/b516a0152842c9b48eb0254dfab72cad3aaf02a8/storeFinder${merchant.merchantIcon}"))
    }
}