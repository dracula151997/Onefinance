package com.onefinance.customerapp.core.presentation.views

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.onefinance.customerapp.R
import com.onefinance.customerapp.databinding.CustomLoadingDialogLayoutBinding
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LoadingDialog @Inject constructor(
    @ActivityContext context: Context,
) {

    private var dialog: Dialog
    private var titleTv: TextView
    private var cardView: CardView
    private var progressBar: ProgressBar
    private var _binding: CustomLoadingDialogLayoutBinding? = null

    init {
        val inflater = (context as Activity).layoutInflater
        _binding = CustomLoadingDialogLayoutBinding.inflate(inflater)
        titleTv = _binding!!.cpTitle
        cardView = _binding!!.cpCardview
        progressBar = _binding!!.cpPbar

        cardView.setCardBackgroundColor(Color.parseColor("#70000000"))
        titleTv.setTextColor(Color.WHITE)
        setColorFilter(
            progressBar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.american_yellow, null)
        )
        dialog = CustomLoadingDialog(context)
        dialog.setOnDismissListener { _binding = null }
        dialog.setContentView(_binding!!.root)
    }

    fun show(title: String? = null) {
        titleTv.text = title
        dialog.show()
    }

    fun stop() {
        dialog.dismiss()
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}

@ActivityScoped
class CustomLoadingDialog @Inject constructor(@ActivityContext context: Context) :
    Dialog(context, R.style.CustomDialogTheme) {
    init {
        setCancelable(false)
        window?.decorView?.rootView?.setBackgroundResource(R.color.dialogBackground)
        window?.decorView?.setOnApplyWindowInsetsListener { v, insets ->
            insets.consumeSystemWindowInsets()
        }
    }

}