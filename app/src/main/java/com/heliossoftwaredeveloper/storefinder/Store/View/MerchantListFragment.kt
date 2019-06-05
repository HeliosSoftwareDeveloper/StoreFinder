/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_merchant_list.*

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


        merchantPresenter.getMerchantList()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewMerchants.layoutManager = LinearLayoutManager(activity)

        etSearchBox.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                merchantListAdapter.filter.filter(s)
            }
        })
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
