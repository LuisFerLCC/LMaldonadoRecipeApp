package io.luisferlcc.ulsb.recipeapp.domain.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.luisferlcc.ulsb.recipeapp.data.KtorfitClient
import io.luisferlcc.ulsb.recipeapp.domain.dtos.Login
import io.luisferlcc.ulsb.recipeapp.domain.dtos.Register
import io.luisferlcc.ulsb.recipeapp.domain.utils.Preferences
import kotlinx.coroutines.launch


class AuthViewModel: ViewModel() {
    var isLoading by mutableStateOf(false)

    fun register(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                val service = KtorfitClient.createAuthService()
                val register = Register(
                    name = name,
                    email = email,
                    password = password
                )
                val result = service.register(register)
                if (result.isLogged) {
                    println("Logueao")
                    Preferences.saveUserId(result.userId)
                    Preferences.saveIsLogged(result.isLogged)
                    println(result.toString())
                    onResult(true, result.toString())
                } else {
                    println("No logueao")
                    println(result.toString())
                    onResult(false, result.toString())
                }
            } catch (ex: Exception) {
                println("Error al iniciar sesión")
                println(ex.toString())
                onResult(false, ex.message ?: "")
            } finally {
                isLoading = false
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                val service = KtorfitClient.createAuthService()
                val login = Login(
                    email = email,
                    password = password
                )
                val result = service.login(login)
                if (result.isLogged) {
                    println("Logueao")
                    Preferences.saveUserId(result.userId)
                    Preferences.saveIsLogged(result.isLogged)
                    println(result.toString())
                    onResult(true, result.toString())
                } else {
                    println("No logueao")
                    println(result.toString())
                    onResult(false, result.toString())
                }
            } catch (ex: Exception) {
                println("Error al iniciar sesión")
                println(ex.toString())
                onResult(false, ex.message ?: "")
            } finally {
                isLoading = false
            }
        }
    }
}