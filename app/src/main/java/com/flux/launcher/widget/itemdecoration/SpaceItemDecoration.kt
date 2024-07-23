package com.flux.launcher.widget.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flux.launcher.util.constant.Constants
import com.flux.launcher.util.extension.toPx

class SpaceItemDecoration(private val horizontalSpace: Int? = null, private val verticalSpace: Int? = null) :
    RecyclerView.ItemDecoration() {

    companion object {
        private const val EDGE_SPACING_FACTOR = 1
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (horizontalSpace != null) {
            outRect.left = horizontalSpace.toPx
            outRect.right = horizontalSpace.toPx
        }

        if (verticalSpace != null) {
            val verticalSpacing = verticalSpace.toPx
            if (parent.getChildLayoutPosition(view) == state.itemCount - 1)
                outRect.bottom = EDGE_SPACING_FACTOR * verticalSpacing
            else
                outRect.bottom = verticalSpacing

            if (parent.getChildLayoutPosition(view) >= 0 && parent.getChildLayoutPosition(view) < Constants.APP_LIST_SPAN_COUNT)
                outRect.top = EDGE_SPACING_FACTOR * verticalSpacing
            else
                outRect.top = verticalSpacing
        }

    }
}