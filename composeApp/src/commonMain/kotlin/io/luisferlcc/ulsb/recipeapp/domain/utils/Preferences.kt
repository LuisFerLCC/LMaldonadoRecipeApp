package io.luisferlcc.ulsb.recipeapp.domain.utils

import com.russhwolf.settings.Settings


object Preferences {
    private val settings = Settings()

    fun saveUserId(userId: Int) {
        settings.putInt("userId", userId)
    }

    fun saveIsLogged(isLogged: Boolean) {
        settings.putBoolean("isLogged", isLogged)
    }

    fun getUserId(): Int? {
        return settings.getIntOrNull("userId")
    }

    fun getIsLogged(): Boolean {
        return settings.getBoolean("isLogged", false)
    }

    fun clearSettings() {
        settings.clear()
    }
}