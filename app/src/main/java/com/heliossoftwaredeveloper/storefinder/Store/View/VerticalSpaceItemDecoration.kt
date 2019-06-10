/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.storefinder.Store.View

import android.graphics.Rect
import android.support.v7.widget.RecyclerView

/**
 * Created by Ruel N. Grajo on 06/06/2019.
 *
 * ItemDecoration class for VerticalSpaceItemDecoration
 */

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView?) {
        super.getItemOffsets(outRect, itemPosition, parent)
        outRect.bottom = verticalSpaceHeight
    }
}