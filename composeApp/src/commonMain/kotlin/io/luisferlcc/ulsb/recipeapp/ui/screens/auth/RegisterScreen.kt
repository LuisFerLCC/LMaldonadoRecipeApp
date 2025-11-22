package io.luisferlcc.ulsb.recipeapp.ui.screens.auth

import androidx.compose.foundation.background
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
import io.luisferlcc.ulsb.recipeapp.ui.RecipeTheme
import io.luisferlcc.ulsb.recipeapp.ui.components.LoadingOverlay
import io.luisferlcc.ulsb.recipeapp.ui.screens.LoginScreenRoute
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun RegisterScreen(
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme
    val viewModel: AuthViewModel = viewModel()

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var isRegistered by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isRegistered) {
        if (isRegistered) {
            navController.navigate(LoginScreenRoute) {
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
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 40.dp,
                            bottomEnd = 40.dp
                        )
                    )
                    .background(colors.primary)
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(400.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(26.dp)
                )
                .clip(RoundedCornerShape(26.dp))
                .background(colors.surface)
                .padding(20.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Crear cuenta",
                color = colors.onSurface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Nombre") },
                shape = CircleShape
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
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Confirmar Contraseña") },
                shape = CircleShape,

                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (
                        name.isBlank() ||
                        email.isBlank() ||
                        password.isBlank() ||
                        confirmPassword.isBlank()
                    ) {
                        return@Button
                    }

                    if (password != confirmPassword) {
                        return@Button
                    }

                    viewModel.register(
                        name = name,
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
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Iniciar sesión")
            }
        }

        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RecipeTheme {
        RegisterScreen(rememberNavController())
    }
}