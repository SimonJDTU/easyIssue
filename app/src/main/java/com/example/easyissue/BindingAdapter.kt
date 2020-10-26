package com.example.easyissue

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.data.Project
import com.example.easyissue.projectScreen.ProjectAdapter

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility= if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun setItems(view: RecyclerView, data: List<Project>) {
    (view.adapter as ProjectAdapter).apply {
        submitList(data)
    }
}

