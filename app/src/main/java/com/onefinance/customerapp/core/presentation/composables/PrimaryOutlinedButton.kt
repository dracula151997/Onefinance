package com.onefinance.customerapp.core.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onefinance.customerapp.R
import com.onefinance.customerapp.ui.theme.poppins

@Composable
fun PrimaryOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(35.dp),
    borderStroke: BorderStroke = BorderStroke(
        1.dp,
        colorResource(id = R.color.metallic_yellow)
    ),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    buttonColors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = Color.Transparent,
        contentColor = colorResource(id = R.color.metallic_yellow)
    ),
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick, shape = shape,
        border = borderStroke,
        modifier = modifier,
        colors = buttonColors,
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontFamily = poppins,
            fontSize = 14.sp
        )
    }
}