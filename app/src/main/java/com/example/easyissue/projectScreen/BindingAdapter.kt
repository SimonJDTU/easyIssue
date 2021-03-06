package com.example.easyissue.projectScreen

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.data.Project

@BindingAdapter("decoration")
fun setDecoration(view: RecyclerView, space: Float) {
    view.addItemDecoration(SpacesItemDecoration(space.toInt()))
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter("items")
fun setItems(view: RecyclerView, data: List<Project>) {
    (view.adapter as ProjectAdapter).apply {
        submitList(data)
    }
}
