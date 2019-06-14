package com.heliossoftwaredeveloper.storefinder

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * Class to handle view navigation
 */

open class ViewNavigator : AppCompatActivity(){

    /**
     * Method to navigate to fragment
     *
     * @param fragmentTo fragment to display
     * @param addToBackstack flag to set if the fragment needs to add to backStack
     */
    fun nagivateTo(fragmentTo : Fragment, addToBackstack : Boolean) {
        val transManager = supportFragmentManager.beginTransaction()
        transManager.add(R.id.layout_container, fragmentTo, fragmentTo.javaClass.name)
        if (addToBackstack) {
            transManager.addToBackStack(fragmentTo.javaClass.name)
        }
        transManager.show(fragmentTo)
        transManager.commit()
    }
}
