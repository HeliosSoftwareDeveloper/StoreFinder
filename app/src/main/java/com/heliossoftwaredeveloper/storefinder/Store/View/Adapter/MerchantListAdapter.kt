package com.heliossoftwaredeveloper.storefinder.Store.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant

/**
 * Created by HELIOS.SOFTWARE.DEVELOPER on 06/06/2019.
 */
class MerchantListAdapter(listMerchant: List<Merchant>) : RecyclerView.Adapter<MerchantListAdapter.MerchantViewHolder>(), Filterable {

    private val mListMerchant = listMerchant
    private var filteredMerchantList : ArrayList<Merchant> = ArrayList()

    class MerchantViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_merchant, parent, false)) {

        private var txtMerchantName : TextView = itemView.findViewById(R.id.txtMerchantName)
        private var txtMerchantDetails : TextView  = itemView.findViewById(R.id.txtMerchantDetails)

        fun bind(merchant: Merchant) {
            txtMerchantName.text = merchant.merchantName
            txtMerchantDetails.text = merchant.merchantDetails
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MerchantViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return filteredMerchantList.size
    }

    override fun onBindViewHolder(holder: MerchantViewHolder, position: Int) {
        holder.bind(filteredMerchantList.get(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredMerchantList = ArrayList()
                } else {
                    val filteredList = ArrayList<Merchant>()
                    for (row in mListMerchant) {
                        if (row.merchantName.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredMerchantList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = filteredMerchantList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                filteredMerchantList = filterResults.values as ArrayList<Merchant>
                notifyDataSetChanged()
            }
        }
    }

}