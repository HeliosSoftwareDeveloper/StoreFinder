/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.Presenter.MerchantListPresenter
import com.heliossoftwaredeveloper.storefinder.Store.Presenter.MerchantListPresenterImpl
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.MerchantListAdapter

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Fragment class that display Merchant List.
 */

class MerchantListFragment : Fragment(), MerchantListView {

    private var mListener: OnMerchantListFragmentListener? = null
    private lateinit var merchantPresenter : MerchantListPresenter
    private lateinit var merchantListAdapter : MerchantListAdapter
    private lateinit var recycleViewMerchants : RecyclerView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnMerchantListFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_merchant_list, container, false)

        merchantPresenter = MerchantListPresenterImpl(this)

        recycleViewMerchants = view.findViewById(R.id.recycleViewMerchants)
        recycleViewMerchants.layoutManager = LinearLayoutManager(activity)

        merchantPresenter.getMerchantList()

        return view
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onUpdateMerchantList(merchantList: List<Merchant>) {
        merchantListAdapter = MerchantListAdapter(merchantList)
        recycleViewMerchants.adapter = merchantListAdapter
    }

    override fun updateLoaderVisibility(isVisible: Boolean) {
    }

    /**
     * Interface to handle callbacks
     * */
    interface OnMerchantListFragmentListener {
        // TODO: Update argument type and name
        fun onMerchantClicked(merchant: Merchant)
    }
}
