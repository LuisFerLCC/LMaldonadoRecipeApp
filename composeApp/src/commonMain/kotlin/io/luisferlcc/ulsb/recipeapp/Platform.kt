package io.luisferlcc.ulsb.recipeapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform