package io.luisferlcc.ulsb.recipeapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import recipeapp.composeapp.generated.resources.Res
import recipeapp.composeapp.generated.resources.poppins_black
import recipeapp.composeapp.generated.resources.poppins_bold
import recipeapp.composeapp.generated.resources.poppins_light
import recipeapp.composeapp.generated.resources.poppins_regular


@Composable
fun poppinsTypography(): Typography {
    val poppinsFontFamily = FontFamily(
        Font(Res.font.poppins_regular),
        Font(Res.font.poppins_light, FontWeight.Light),
        Font(Res.font.poppins_bold, FontWeight.Bold),
        Font(Res.font.poppins_black, FontWeight.Black)
    )

    return with(MaterialTheme.typography) {
        copy(
            displayLarge = displayLarge.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            displayMedium = displayMedium.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            displaySmall = displaySmall.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            headlineLarge = headlineLarge.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            headlineMedium = headlineMedium.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            headlineSmall = headlineSmall.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            titleLarge = titleLarge.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            titleMedium = titleMedium.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            titleSmall = titleSmall.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold),
            labelLarge = labelLarge.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal),
            labelMedium = labelMedium.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal),
            labelSmall = labelSmall.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal),
            bodyLarge = bodyLarge.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal),
            bodyMedium = bodyMedium.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal),
            bodySmall = bodySmall.copy(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal),
        )
    }
}


