package com.onefinance.customerapp.core.presentation.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.onefinance.customerapp.R
import com.onefinance.customerapp.databinding.PrimaryEditTextBinding

class PrimaryEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var _binding: PrimaryEditTextBinding? = null

    var label: String = ""
        private set
    var inputType: Int = InputType.TYPE_CLASS_TEXT
        private set
    var maxLength: Int = Int.MAX_VALUE
        private set
    var editText: EditText? = null
        private set
    var iconDrawable: Drawable? = null
        private set
    var showPasswordToggle: Boolean = false
        private set
    var isPasswordToggleChecked: Boolean = false
        private set

    init {
        orientation = VERTICAL
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = PrimaryEditTextBinding.inflate(inflater, this)
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryEditText, 0, 0).apply {
            try {
                label = getString(R.styleable.PrimaryEditText_android_label).orEmpty()
                inputType = getInt(
                    R.styleable.PrimaryEditText_android_inputType,
                    InputType.TYPE_CLASS_TEXT
                )
                maxLength = getInt(R.styleable.PrimaryEditText_android_maxLength, Int.MAX_VALUE)
                iconDrawable = getDrawable(R.styleable.PrimaryEditText_trailingIcon)
                showPasswordToggle =
                    getBoolean(R.styleable.PrimaryEditText_showPasswordToggleIcon, false)
                _binding?.let {
                    editText = it.editText
                    it.label.text = label
                    it.editText.inputType = inputType
                    it.editText.filters = arrayOf(
                        InputFilter.LengthFilter(
                            maxLength
                        )
                    )
                    setTrailingIcon(it)
                    editText?.setOnFocusChangeListener { v, hasFocus ->
                        if (hasFocus) {
                            it.mainLayout.setBackgroundResource(R.drawable.drawable_primary_text_field_rounded_focused)
                        } else {
                            it.mainLayout.setBackgroundResource(R.drawable.drawable_primary_text_field_rounded)
                        }
                    }
                    it.passwordVisibilty.isVisible = showPasswordToggle
                    it.passwordVisibilty.setOnClickListener { view ->
                        isPasswordToggleChecked = !isPasswordToggleChecked
                        if (!isPasswordToggleChecked) {
                            editText?.transformationMethod = PasswordTransformationMethod.getInstance()
                            editText?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            editText?.setText(editText?.text.toString())
                            editText?.setSelection(editText?.text?.length ?: 0)
                            it.passwordVisibilty.setImageDrawable(null)
                            it.passwordVisibilty.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24)
                        } else {
                            editText?.transformationMethod = HideReturnsTransformationMethod.getInstance()
                            editText?.inputType = InputType.TYPE_CLASS_TEXT;
                            editText?.setText(editText?.text.toString());
                            editText?.setSelection(editText?.text?.length ?: 0);
                            it.passwordVisibilty.setImageDrawable(null)
                            it.passwordVisibilty.setBackgroundResource(R.drawable.ic_baseline_visibility_24)

                        }

                    }

                }
            } finally {
                recycle()
            }
        }

    }

    private fun setTrailingIcon(it: PrimaryEditTextBinding) {
        it.divider.isVisible = iconDrawable != null
        if (iconDrawable != null) {
            it.icon.setImageDrawable(iconDrawable)
        } else {
            it.icon.isVisible = false
        }
    }

    fun setError(error: String?) {
        if (error == null)
            _binding?.errorTxt?.isVisible = false
        else {
            _binding?.errorTxt?.isVisible = true
            _binding?.errorTxt?.text = error
        }
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }


}