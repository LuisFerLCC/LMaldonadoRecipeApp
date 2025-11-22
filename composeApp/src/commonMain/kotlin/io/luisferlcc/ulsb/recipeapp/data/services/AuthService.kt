package io.luisferlcc.ulsb.recipeapp.data.services

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import io.luisferlcc.ulsb.recipeapp.domain.dtos.AuthResponse
import io.luisferlcc.ulsb.recipeapp.domain.dtos.Login
import io.luisferlcc.ulsb.recipeapp.domain.dtos.Register


interface AuthService {
    @POST(value = "auth/login")
    suspend fun login(@Body login: Login): AuthResponse

    @POST(value = "auth/register")
    suspend fun register(@Body register: Register): AuthResponse
}
