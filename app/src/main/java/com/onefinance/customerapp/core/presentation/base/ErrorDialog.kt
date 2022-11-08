package com.onefinance.customerapp.core.presentation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.core.presentation.extensions.setWidthPercent
import com.onefinance.customerapp.databinding.ErrorLayoutDialogBinding

class ErrorDialog(private var message: String?) : DialogFragment(),
    ViewBindingHolder<ErrorLayoutDialogBinding> by ViewBindingHolderImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWidthPercent(85)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(ErrorLayoutDialogBinding.inflate(layoutInflater), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requireBinding {
            tvDialogError.text = message
            continueBtn.setOnClickListener { dismiss() }
        }

    }
}