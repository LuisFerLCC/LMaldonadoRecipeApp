package io.luisferlcc.ulsb.recipeapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.luisferlcc.ulsb.recipeapp.domain.models.AuthViewModel
import io.luisferlcc.ulsb.recipeapp.domain.utils.Preferences
import io.luisferlcc.ulsb.recipeapp.ui.RecipeTheme
import io.luisferlcc.ulsb.recipeapp.ui.components.LoadingOverlay
import io.luisferlcc.ulsb.recipeapp.ui.screens.HomeScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.LoginScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.RegisterScreenRoute
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun LoginScreen(
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme
    val viewModel: AuthViewModel = viewModel()

    var isLogged by remember {
        mutableStateOf(Preferences.getIsLogged())
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(isLogged) {
        if (isLogged) {
            navController.navigate(HomeScreenRoute) {
                popUpTo(LoginScreenRoute) { inclusive = true }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    ))
                    .background(colors.primary)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(320.dp)
                .padding(horizontal = 10.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(colors.surface)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement
                .spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido",
                color = colors.onSurface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Correo Electrónico") },
                shape = CircleShape
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Contraseña") },
                shape = CircleShape,

                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (
                        email.isBlank() ||
                        password.isBlank()
                    ) {
                        return@Button
                    }

                    viewModel.login(
                        email = email,
                        password = password
                    ) { result, message ->
                        if (result) {
                            navController.navigate(LoginScreenRoute) {
                                popUpTo(LoginScreenRoute) {
                                    inclusive = true
                                }
                            }
                        } else {
                            println(message)
                        }
                    }
                }
            ) {
                Text(text = "Iniciar sesión")
            }

            Text(
                text = "¿No tienes una cuenta? Crea una.",
                modifier = Modifier
                    .clickable {
                        navController.navigate(RegisterScreenRoute)
                    },
                color = colors.primary,
                fontWeight = FontWeight.Bold
            )
        }

        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    RecipeTheme {
        LoginScreen(rememberNavController())
    }
}