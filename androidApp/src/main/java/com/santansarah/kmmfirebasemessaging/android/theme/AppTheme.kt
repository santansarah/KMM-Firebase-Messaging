package com.santansarah.kmmfirebasemessaging.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santansarah.kmmfirebasemessaging.SharedRes

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
//    val colors = lightColorScheme(
//        primary = colorResource(id = SharedRes.colors.primary.resourceId),
//        onPrimary = colorResource(id = SharedRes.colors.lightText.resourceId),
//        onSecondary = colorResource(id = SharedRes.colors.darkText.resourceId),
//        surfaceVariant = colorResource(id = SharedRes.colors.cardSurface.resourceId),
//        background = colorResource(id = SharedRes.colors.background.resourceId),
//        surface = colorResource(id = SharedRes.colors.background.resourceId)
//    )

    val colors = mokoColorScheme()

    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )

}