package com.onefinance.customerapp.core.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.utils.UiText
import com.onefinance.customerapp.ui.theme.OnefinanceTheme

@Composable
fun PrimaryTextField(
    icon: Painter,
    text: String,
    hint: UiText,
    modifier: Modifier = Modifier,
    isPasswordField: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChanged: (String) -> Unit,

    ) {
    BasicTextField(
        value = text,
        onValueChange = onValueChanged,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .heightIn(60.dp),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .background(
                        colorResource(id = R.color.text_field_background_color),
                        RoundedCornerShape(percent = 30),
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = icon, contentDescription = null)
                Spacer(Modifier.width(16.dp))
                Divider(
                    modifier = Modifier
                        .height(54.dp)
                        .width(1.dp)
                )
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun PrimaryTextFieldPreview() {
    OnefinanceTheme {
        PrimaryTextField(
            icon = painterResource(id = com.onefinance.customerapp.R.drawable.ic_national_id),
            text = "",
            hint = UiText.DynamicString("Hello"),
            modifier = Modifier.padding(12.dp),
            onValueChanged = {

            }
        )
    }
}