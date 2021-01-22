package com.example.easyissue

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility= if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("setErrorText")
fun setErrorText(view: TextInputLayout, msg: String) {
    view.error = msg
}