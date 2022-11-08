package com.onefinance.customerapp.core.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.onefinance.customerapp.R
import com.onefinance.customerapp.ui.theme.poppins


@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    shape: Shape = RoundedCornerShape(35.dp),
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(id = R.color.metallic_yellow),
        contentColor = colorResource(id = R.color.black),
    ),
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    paddingValues: PaddingValues = PaddingValues(0.dp),
    elevation: ButtonElevation = ButtonDefaults.elevation(defaultElevation = 0.dp, focusedElevation = 0.dp),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = buttonColors,
        border = borderStroke,
        contentPadding = paddingValues,
        elevation = elevation
    ) {
        Text(text = text, style = textStyle, fontWeight = FontWeight.Medium, fontFamily = poppins)
    }
}