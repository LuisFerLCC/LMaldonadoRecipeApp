package io.luisferlcc.ulsb.recipeapp.ui.screens.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable


@Composable
fun AuthBackground(
    content: @Composable () -> Unit
) {
    Box() {
        content()
    }
}