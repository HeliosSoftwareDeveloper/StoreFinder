/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.SharedComponents

import android.Manifest

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Object class for constant variables
 */
object Constants {
    //User Permission section
    const val REQUEST_CODE_USER_PERMISSION_ALL = 1001
    const val REQUEST_CODE_USER_LOCATION_PERMISSION = 1002
    val listUserRequiredPermission = arrayOf( Manifest.permission.ACCESS_FINE_LOCATION)

    //Map section
    const val MAP_ZOOM_LEVEL = 17.0f
}