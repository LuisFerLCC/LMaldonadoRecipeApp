package io.luisferlcc.ulsb.recipeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import io.luisferlcc.ulsb.recipeapp.domain.dtos.Prompt
import io.luisferlcc.ulsb.recipeapp.domain.dtos.RecipeDTO
import io.luisferlcc.ulsb.recipeapp.domain.models.Recipe
import io.luisferlcc.ulsb.recipeapp.domain.models.RecipeViewModel
import io.luisferlcc.ulsb.recipeapp.domain.utils.Preferences
import io.luisferlcc.ulsb.recipeapp.domain.utils.hideKeyboard
import io.luisferlcc.ulsb.recipeapp.ui.RecipeTheme
import io.luisferlcc.ulsb.recipeapp.ui.components.CustomOutlinedTextField
import io.luisferlcc.ulsb.recipeapp.ui.components.LoadingOverlay
import io.luisferlcc.ulsb.recipeapp.ui.screens.HomeScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.LoginScreenRoute
import io.luisferlcc.ulsb.recipeapp.ui.screens.home.components.GeneratedRecipe
import io.luisferlcc.ulsb.recipeapp.ui.screens.home.components.Header
import io.luisferlcc.ulsb.recipeapp.ui.screens.home.components.RecipeCard
import io.luisferlcc.ulsb.recipeapp.ui.screens.home.components.RecipeItem
import io.luisferlcc.ulsb.recipeapp.ui.screens.home.components.Tag
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: RecipeViewModel = viewModel()

    val colors = MaterialTheme.colorScheme
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    var prompt by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .safeContentPadding()
            .padding(5.dp)
    ) {

        // Header
        item {
            Header(
                onLogout = {
                    Preferences.clearSettings()
                    navController.navigate(LoginScreenRoute) {
                        popUpTo(HomeScreenRoute) { inclusive = true }
                    }
                }
            )
        }

        item {
            Text(
                text = "Crea, cocina, comparte y disfruta",
                color = colors.onSurface,
                style = MaterialTheme.typography.headlineMedium
                    .copy(fontWeight = FontWeight.ExtraBold)
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomOutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                trailingIcon = Icons.Default.AutoAwesome,
                onTrailingIconClick = {
                    hideKeyboard(focusManager)

                    viewModel.generateRecipe(
                        prompt = Prompt(
                            ingredients = prompt
                        )
                    )

                    scope.launch { sheetState.partialExpand() }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                placeholderString = "Escribe tus ingredientes...",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        hideKeyboard(focusManager)

                        viewModel.generateRecipe(
                            prompt = Prompt(
                                ingredients = prompt
                            )
                        )

                        viewModel.showSheet = true
                        scope.launch { sheetState.partialExpand() }
                    }
                )
            )
        }

        item {
            Text(
                text = "Tus recetas recientes",
                modifier = Modifier
                    .padding(bottom = 10.dp),
                color = colors.onSurface,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineSmall
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement
                    .spacedBy(10.dp)
            ) {
                items(viewModel.recipes.take(5)) { recipe ->
                    RecipeCard(recipe) {
                        scope.launch {
                            val recipeDTO = RecipeDTO(
                                category = recipe.category,
                                ingredients = recipe.ingredients,
                                instructions = recipe.instructions,
                                minutes = recipe.minutes,
                                prompt = "",
                                stars = recipe.stars,
                                title = recipe.title,
                                imageUrl = recipe.imageUrl ?: ""
                            )

                            viewModel.showModalFromList(recipe = recipeDTO)
                            sheetState.partialExpand()
                        }
                    }
                }
            }
        }

        // Quick Ideas
        item {
            val tags = listOf(
                "Rápidas (10 min)",
                "Pocas calorías",
                "Sin horno",
                "Desayuno"
            )

            Text(
                text = "Ideas rápidas",
                modifier = Modifier
                    .padding(vertical = 5.dp),
                color = colors.onSurface
            )

            LazyRow(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tags) { tag ->
                    Tag(
                        innerPadding = PaddingValues(
                            horizontal = 14.dp,
                            vertical = 6.dp
                        )
                    ) {
                        Text(
                            text = tag,
                            color = colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.primary.copy(alpha = 0.2f))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .clickable {},
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "¿No sabes qué cocinar hoy?",
                        color = colors.onSurface
                    )
                    Text(
                        text = "Genera una receta aleatoria",
                        color = colors.onSurface
                    )
                }

                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = colors.onSurface
                )
            }

        }

        item {
            Text(
                text = "Todas tus recetas",
                modifier = Modifier
                    .padding(bottom = 10.dp),
                color = colors.onSurface,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        items(viewModel.recipes) { recipe ->
            RecipeItem(recipe) {
                scope.launch {
                    val recipeDTO = RecipeDTO(
                        category = recipe.category,
                        ingredients = recipe.ingredients,
                        instructions = recipe.instructions,
                        minutes = recipe.minutes,
                        prompt = "",
                        stars = recipe.stars,
                        title = recipe.title,
                        imageUrl = recipe.imageUrl ?: ""
                    )

                    viewModel.showModalFromList(recipe = recipeDTO)
                    sheetState.partialExpand()
                }
            }
        }
    }

    if (viewModel.showSheet && viewModel.generatedRecipe != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.hideModal() },
            dragHandle = { BottomSheetDefaults.DragHandle() },
            sheetState = sheetState,
            containerColor = colors.surface
        ) {
            GeneratedRecipe(
                recipe = viewModel.generatedRecipe!!,
                recipeIsSaved = viewModel.generatedRecipeIsSaved,
                onSave = {
                    scope.launch {
                        viewModel.hideModal()
                        sheetState.hide()
                    }

                    if (!viewModel.generatedRecipeIsSaved) { viewModel.saveRecipeInDB() }
                }
            )
        }
    }

    if (viewModel.isLoading) {
        LoadingOverlay()
    }
}




@Preview(
    showBackground = true,
)
@Composable
fun HomeScreenPreview() {
    RecipeTheme {
        HomeScreen(rememberNavController())
    }
}