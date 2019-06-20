/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.ViewHolder.MerchantHeaderViewHolder
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.ViewHolder.MerchantViewHolder

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Adapter class for MerchantList
 */

class MerchantListAdapter(merchantListAdapterListener : MerchantListAdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var cachedMerchantList : List<MerchantListItem> = ArrayList()
    private var filteredMerchantList : List<MerchantListItem> = ArrayList()
    private var mMerchantListAdapterListener = merchantListAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){
            MerchantListItem.MerchantListItemType.ITEM_HEADER.ordinal -> MerchantHeaderViewHolder(inflater, parent)
            else -> MerchantViewHolder(inflater, parent)
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
            is MerchantViewHolder -> holder.bind(filteredMerchantList.get(position).merchant!!, mMerchantListAdapterListener)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                if (charSequence.isEmpty()) {
                    return Filter.FilterResults().apply {
                        this.values = cachedMerchantList
                    }
                }

                return Filter.FilterResults().apply {
                    val charString = charSequence.toString()
                    val filteredList = ArrayList<MerchantListItem>()
                    for (row in cachedMerchantList) {
                        when (row.merchantListItemType) {
                            MerchantListItem.MerchantListItemType.ITEM_HEADER -> {
                                removeEmptyChildHeaderItem(filteredList)
                                filteredList.add(row)
                            }
                            else -> if (row.merchant?.merchantName!!.contains(charString.toLowerCase(), true)) filteredList.add(row)
                        }
                    }
                    removeEmptyChildHeaderItem(filteredList)
                    this.values = filteredList
                }
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                filteredMerchantList = filterResults.values as ArrayList<MerchantListItem>
                notifyDataSetChanged()
            }
        }
    }

    /**
     * Interface for callback of items to trigger events on fragment/activity
     */
    interface MerchantListAdapterListener {
        fun onMerchantItemListClicked(merchant : MerchantItem)
    }

    /**
     * Method to remove the header that doesn't have a child after search filter
     *
     * @param filteredList list in where the headerItem should be removed
     * */
    private fun removeEmptyChildHeaderItem(filteredList : ArrayList<MerchantListItem>) {
        if (filteredList.size > 0 && filteredList.get(filteredList.lastIndex).category != null) {
            filteredList.removeAt(filteredList.lastIndex)
        }
    }

    /**
     * Method to update the adapter list
     *
     * @param listMerchant listItems to add
     * */
    fun updateList(listMerchant: List<MerchantListItem>) {
        cachedMerchantList = listMerchant
        filteredMerchantList = listMerchant
        notifyDataSetChanged()
    }
}
