package com.example.easyissue

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility= if (visible) View.VISIBLE else View.GONE
}
