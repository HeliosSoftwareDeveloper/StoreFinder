/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View.Fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import com.heliossoftwaredeveloper.storefinder.SharedViewComponents.DividerSpaceItemDecoration

import com.heliossoftwaredeveloper.storefinder.R
import com.heliossoftwaredeveloper.storefinder.SharedViewComponents.Constants
import com.heliossoftwaredeveloper.storefinder.Store.Model.MerchantItem
import com.heliossoftwaredeveloper.storefinder.Store.Presenter.MerchantDetailsPresenter
import com.heliossoftwaredeveloper.storefinder.Store.Presenter.MerchantDetailsPresenterImpl
import com.heliossoftwaredeveloper.storefinder.Store.View.Adapter.MerchantBranchesAdapter
import com.heliossoftwaredeveloper.storefinder.Store.View.MerchantDetailsView
import kotlinx.android.synthetic.main.fragment_merchant_details.*

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Fragment class that display MerchantItem complete details.
 */

class MerchantDetailsFragment : Fragment(), OnMapReadyCallback, MerchantBranchesAdapter.MerchantBranchesAdapterListener, MerchantDetailsView {

    private var selectedMerchant: MerchantItem? = null
    private lateinit var mMap: GoogleMap
    private lateinit var merchantDetailsPresenter : MerchantDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedMerchant = arguments.getSerializable(ARG_PARAM_MERCHANT) as MerchantItem
        }
        merchantDetailsPresenter = MerchantDetailsPresenterImpl(this)
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
        recycleViewMerchantBranches.adapter = MerchantBranchesAdapter(selectedMerchant!!.merchantBranches, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.REQUEST_CODE_USER_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    mMap.isMyLocationEnabled = true
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_CODE_USER_LOCATION_PERMISSION)
        }

        merchantDetailsPresenter.getMerchantBranchMarkers(selectedMerchant!!)
    }

    companion object {
        private const val ARG_PARAM_MERCHANT = "argParamMerchant"

        fun newInstance(merchant: MerchantItem): MerchantDetailsFragment {
            val fragment = MerchantDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM_MERCHANT, merchant)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMerchantBranchItemListClicked(merchantBranch: MerchantItem.Branches) {
        merchantDetailsPresenter.getMerchantBranchLocation(merchantBranch)
    }

    override fun onAddMapMarker(markerOptions: MarkerOptions) {
        mMap.addMarker(markerOptions)
    }

    override fun onMoveMapLocationTo(location: LatLng) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(Constants.MAP_ZOOM_LEVEL))
        }
    }
}
