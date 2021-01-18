package com.example.easyissue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_confirmation.view.*
import kotlinx.android.synthetic.main.dialog_error.view.*

class InteractiveDialog(private val listener: OnPositiveSelected, private val dialogType: DialogType) :
    DialogFragment() {

    interface OnPositiveSelected {
        fun dialogPositiveOption()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (dialogType) {
            is DialogType.Proceed -> {
                inflater.inflate(R.layout.dialog_confirmation, container, false)
            }
            is DialogType.Error -> {
                inflater.inflate(R.layout.dialog_error, container, false)
            }
            DialogType.LoggedOut -> {
                inflater.inflate(R.layout.dialog_informative, container, false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
        when (dialogType) {
            is DialogType.Proceed -> {
                view.btnPositive.setOnClickListener {
                    listener.dialogPositiveOption()
                    dismiss()
                }
                view.btnNegative.setOnClickListener {
                    dismiss()
                }
            }
            is DialogType.Error, DialogType.LoggedOut -> {
                view.btnProceed.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    //TODO: process messages in "Proceed" instead of creating new objects
    sealed class DialogType {
        object Proceed : DialogType()
        object LoggedOut : DialogType()
        data class Error(val errorMsg: Throwable? = null) : DialogType()
    }
}