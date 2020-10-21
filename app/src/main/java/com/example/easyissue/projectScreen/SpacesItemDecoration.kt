package com.example.easyissue.projectScreen

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//Item decorator class for recyclerview
class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        when {
            position == 0 || position%2 == 0 -> {
                outRect.bottom = space
                outRect.right = space
            }
            position == 1 || position%2 != 0 -> {
                outRect.bottom = space
            }
        }
    }
}