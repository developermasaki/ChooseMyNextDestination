package com.example.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.example.choosemynextdestination.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


val kaiseiFontFamily = FontFamily(
    Font(R.font.kaiseidecol_bold, FontWeight.Bold),
    Font(R.font.kaiseidecol_medium, FontWeight.Medium)
)

val shipporiFontFamily = FontFamily(
    Font(R.font.shippori_mincho_regular, FontWeight.Normal),
    Font(R.font.shippori_mincho_medium, FontWeight.Medium)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = kaiseiFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = kaiseiFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = shipporiFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = shipporiFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = shipporiFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

