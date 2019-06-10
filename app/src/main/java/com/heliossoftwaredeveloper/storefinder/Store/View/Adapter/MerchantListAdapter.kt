/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Adapter class for MerchantList
 */

class MerchantListAdapter(listMerchant: List<MerchantListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var mListMerchant = listMerchant
    private var filteredMerchantList = listMerchant

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){
            MerchantListItem.MerchantListItemType.ITEM_HEADER.ordinal ->  MerchantHeaderViewHolder(inflater, parent)
            else -> return MerchantViewHolder(inflater, parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return filteredMerchantList.get(position).merchantListItemType.ordinal
    }

    override fun getItemCount(): Int {
        return filteredMerchantList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MerchantHeaderViewHolder -> holder.bind(filteredMerchantList.get(position).category!!)
            is MerchantViewHolder -> holder.bind(filteredMerchantList.get(position).merchant!!)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    return Filter.FilterResults().apply {
                        this.values = mListMerchant
                    }
                }
                val filteredList = ArrayList<MerchantListItem>()
                for (row in mListMerchant) {
                    when (row.merchantListItemType) {
                        MerchantListItem.MerchantListItemType.ITEM_HEADER -> {
                            removeEmptyChildHeaderItem(filteredList)
                            filteredList.add(row)
                        }
                        else -> if (row.merchant?.merchantName!!.contains(charString.toLowerCase(), true)) filteredList.add(row)
                    }
                }
                removeEmptyChildHeaderItem(filteredList)

                return Filter.FilterResults().apply {
                    this.values = filteredList
                }
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                filteredMerchantList = filterResults.values as ArrayList<MerchantListItem>
                notifyDataSetChanged()
            }
        }
    }

    //Remove the last header item without children
    fun removeEmptyChildHeaderItem(filteredList : ArrayList<MerchantListItem>) {
        if (filteredList.size > 0 && filteredList.get(filteredList.lastIndex).category != null) {
            filteredList.removeAt(filteredList.lastIndex)
        }
    }

    fun updateList(listMerchant: List<MerchantListItem>) {
        mListMerchant = listMerchant
        filteredMerchantList = listMerchant
        notifyDataSetChanged()
    }
}