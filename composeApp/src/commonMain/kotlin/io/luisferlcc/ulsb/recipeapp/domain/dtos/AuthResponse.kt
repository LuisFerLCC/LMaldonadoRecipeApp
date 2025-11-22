package io.luisferlcc.ulsb.recipeapp.domain.dtos

import kotlinx.serialization.Serializable


@Serializable
data class AuthResponse(
    val message: String,
    val isLogged: Boolean,
    val userId: Int,
)
