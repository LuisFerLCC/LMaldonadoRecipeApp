package io.luisferlcc.ulsb.recipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.luisferlcc.ulsb.recipeapp.ui.RecipeTheme
import io.luisferlcc.ulsb.recipeapp.ui.screens.HomeScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.LoginScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.RegisterScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.auth.LoginScreen
import io.luisferlcc.ulsb.recipeapp.ui.screens.auth.RegisterScreen
import io.luisferlcc.ulsb.recipeapp.ui.screens.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    RecipeTheme {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = LoginScreenRoute
        ) {
            composable<LoginScreenRoute> {
                LoginScreen(navController)
            }

            composable<RegisterScreenRoute> {
                RegisterScreen(navController)
            }

            composable<HomeScreenRoute> {
                HomeScreen(navController)
            }
        }

    }
}