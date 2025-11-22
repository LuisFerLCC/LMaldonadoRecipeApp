package io.luisferlcc.ulsb.recipeapp.domain.dtos

import kotlinx.serialization.Serializable


@Serializable
data class Login(
    val email: String,
    val password: String
)
