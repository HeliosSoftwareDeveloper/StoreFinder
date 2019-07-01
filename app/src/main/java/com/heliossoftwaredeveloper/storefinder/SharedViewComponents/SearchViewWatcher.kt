/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.SharedViewComponents

import android.support.v7.widget.SearchView

/**
 * Created by Ruel N. Grajo on 07/01/2019.
 *
 * Text watcher class for searchview.
 */

class SearchViewWatcher {

    lateinit var mlistener : SearchViewWatcherListener

    fun setListener(listener : SearchViewWatcherListener) : SearchViewWatcher {
        mlistener = listener
        return this
    }

    fun registerSearchView(etSearchBox : SearchView) : SearchViewWatcher {
        etSearchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                mlistener.onQueryChanged(newText)
                return true
            }
        })
        return this
    }

    interface SearchViewWatcherListener {
        fun onQueryChanged(newText: String?)
    }
}