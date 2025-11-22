package io.luisferlcc.ulsb.recipeapp.data

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.luisferlcc.ulsb.recipeapp.data.services.AuthService
import io.luisferlcc.ulsb.recipeapp.data.services.RecipeService
import io.luisferlcc.ulsb.recipeapp.data.services.createAuthService
import io.luisferlcc.ulsb.recipeapp.data.services.createRecipeService
import kotlinx.serialization.json.Json


object KtorfitClient {
    val BASE_URL = "https://recipes.pjasoft.com/api/"

    val httpClient = HttpClient {
        expectSuccess = false

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 40000
            socketTimeoutMillis = 40000
            connectTimeoutMillis = 40000
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    private val ktorfit = Ktorfit.Builder()
        .baseUrl(BASE_URL)
        .httpClient(httpClient)
        .build()

    fun createAuthService(): AuthService {
        return ktorfit.createAuthService()
    }

    fun createRecipeService(): RecipeService {
        return ktorfit.createRecipeService()
    }
}