/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_merchant_list.*

import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Presenter.MerchantListPresenter
import com.heliossoftwaredeveloper.storefinder.Store.Presenter.MerchantListPresenterImpl
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.MerchantListAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.SearchView
import com.heliossoftwaredeveloper.storefinder.API.RetrofitClientInstance
import com.heliossoftwaredeveloper.storefinder.SharedComponents.DividerSpaceItemDecoration
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantListItem
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantListView

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Fragment class that display Merchant List.
 */

class MerchantListFragment : Fragment(), MerchantListView, MerchantListAdapter.MerchantListAdapterListener {

    private var mListener: OnMerchantListFragmentListener? = null
    private lateinit var merchantPresenter : MerchantListPresenter
    private lateinit var merchantListAdapter : MerchantListAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnMerchantListFragmentListener) {
            mListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_merchant_list, container, false)
        val apiService by lazy { RetrofitClientInstance.create() }
        merchantPresenter = MerchantListPresenterImpl(this, apiService)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewMerchants.layoutManager = LinearLayoutManager(activity)
        recycleViewMerchants.addItemDecoration(DividerSpaceItemDecoration(resources.getDimension(R.dimen.margin_small).toInt(), false))

        merchantListAdapter = MerchantListAdapter(ArrayList(), this)
        recycleViewMerchants.adapter = merchantListAdapter

        pullToRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            merchantPresenter.getMerchantList()
        })

        etSearchBox.queryHint = resources.getString(R.string.label_search_merchant)
        etSearchBox.setIconifiedByDefault(false)
        etSearchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                merchantListAdapter.filter.filter(newText)
                return true
            }
        })

        merchantPresenter.getMerchantList()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        merchantPresenter.onDestroy()
    }

    override fun onUpdateMerchantList(merchantListItem: List<MerchantListItem>) {
        merchantListAdapter.updateList(merchantListItem)
    }

    override fun updateLoaderVisibility(isVisible: Boolean) {
        pullToRefreshLayout.isRefreshing = isVisible
    }

    override fun showErrorMessage(message: String) {
        //show error message
    }

    override fun onMerchantItemListClicked(merchant: Merchant) {
        mListener?.onMerchantClicked(merchant)
    }

    /**
     * Interface to handle callbacks
     * */
    interface OnMerchantListFragmentListener {
        fun onMerchantClicked(merchant: Merchant)
    }
}
