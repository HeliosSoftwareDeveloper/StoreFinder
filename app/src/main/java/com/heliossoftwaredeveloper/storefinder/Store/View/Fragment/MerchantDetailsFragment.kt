/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.heliossoftwaredeveloper.storefinder.Utils.DividerSpaceItemDecoration

import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.Store.Model.Merchant
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.MerchantBranchesAdapter
import kotlinx.android.synthetic.main.fragment_merchant_details.*

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Fragment class that display Merchant complete details.
 */

class MerchantDetailsFragment : Fragment(), OnMapReadyCallback, MerchantBranchesAdapter.MerchantBranchesAdapterListener {

    private var selectedMerchant: Merchant? = null
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedMerchant = arguments.getSerializable(ARG_PARAM_MERCHANT) as Merchant
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_merchant_details, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtMerchantName.text = selectedMerchant?.merchantName
        txtMerchantDetails.text = selectedMerchant?.merchantDetails
        txtMerchantWebsite.text = selectedMerchant?.merchantWebsite

        recycleViewMerchantBranches.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recycleViewMerchantBranches.addItemDecoration(DividerSpaceItemDecoration(resources.getDimension(R.dimen.margin_small).toInt(), true))
        recycleViewMerchantBranches.adapter = MerchantBranchesAdapter(selectedMerchant?.merchantBranches!!, this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var merchantLocation : LatLng? = null //need to save the instance to move & zoom the map camera to the last branch of merchant.

        for (merchantBranches in selectedMerchant!!.merchantBranches) {
            val brachLocation = merchantBranches.branchLocation
            merchantLocation = LatLng(brachLocation.first(), brachLocation.get(brachLocation.lastIndex))
            mMap.addMarker(MarkerOptions().position(merchantLocation).title(selectedMerchant!!.merchantName))
        }

        if (merchantLocation != null) {
            moveMapCameraTo(merchantLocation)
        }
    }

    companion object {
        const val ARG_PARAM_MERCHANT = "argParamMerchant"

        fun newInstance(merchant: Merchant): MerchantDetailsFragment {
            val fragment = MerchantDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM_MERCHANT, merchant)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMerchantBranchItemListClicked(merchantBranch: Merchant.Branches) {
        val brachLocation = merchantBranch.branchLocation
        moveMapCameraTo(LatLng(brachLocation.first(), brachLocation.get(brachLocation.lastIndex)))
    }

    /**
     * Method to move & zoom the map
     *
     * @param latLng location to zoom
     * */
    fun moveMapCameraTo(latLng: LatLng) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
        }
    }
}
