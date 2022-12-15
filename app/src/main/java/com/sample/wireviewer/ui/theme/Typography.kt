package com.sample.wireviewer.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sample.wireviewer.R

val Rubik = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_light, FontWeight.W500),
    Font(R.font.rubik_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)