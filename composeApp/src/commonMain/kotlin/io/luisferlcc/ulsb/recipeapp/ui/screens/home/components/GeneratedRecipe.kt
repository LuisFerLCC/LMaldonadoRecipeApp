package io.luisferlcc.ulsb.recipeapp.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.luisferlcc.ulsb.recipeapp.domain.dtos.RecipeDTO
import io.luisferlcc.ulsb.recipeapp.ui.RecipeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun GeneratedRecipe(recipe: RecipeDTO, recipeIsSaved: Boolean, onSave: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.primary.copy(alpha = 0.1f)),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Text(
            text = recipe.title,
            color = colors.onSurface,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Tag(
            backgroundColor = colors.primary.copy(alpha = 0.1f),
            innerPadding = PaddingValues(
                horizontal = 14.dp,
                vertical = 6.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "stars",
                tint = colors.primary
            )

            Text(
                text = recipe.stars.toString(),
                color = colors.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = "time",
                tint = colors.primary
            )

            Text(
                text = "${recipe.minutes} min",
                color = colors.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = recipe.category,
                color = colors.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Text(
            text = "Ingredientes",
            color = colors.onSurface,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.headlineSmall
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            recipe.ingredients.forEach { ingredient ->
                Tag(
                    backgroundColor = colors.primary.copy(alpha = 0.1f),
                    innerPadding = PaddingValues(
                        horizontal = 14.dp,
                        vertical = 6.dp
                    )
                ) {
                    Text(
                        text = ingredient,
                        color = colors.primary
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Text(
            text = "Preparación",
            color = colors.onSurface,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.headlineSmall
        )

        recipe.instructions.forEachIndexed { index, instruction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${ index + 1 }. ",
                    color = colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = instruction,
                    color = colors.onSurface
                )
            }
        }

        Button(onClick = onSave) {
            Text(if (recipeIsSaved) "Cerrar" else "Guardar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeneratedRecipePreview() {
    val recipe = RecipeDTO(
        category = "Mexicana",
        imageUrl = "https://images.unsplash.com/photo-1613585435238-5577aa11505f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w4MTg0MDZ8MHwxfHNlYXJjaHw4fHxUb3N0YWRhc3xlbnwwfHx8fDE3NjA5MTU5NzF8MA&ixlib=rb-4.1.0&q=80&w=1080",
        ingredients = listOf(
            "ajo",
            "jamón",
            "jitomate",
            "cebolla",
            "queso",
            "pan",
            "aguacate",
            "aceite de oliva",
            "sal",
            "azúcar",
            "agua",
            "pimienta negra"
        ),
        instructions = listOf(
            "Reúne y prepara: pica finamente 1 diente de ajo, pica 1/2 cebolla en cubos pequeños, corta 1 jitomate en cubos pequeños, corta el jamón en tiras o cuadros, ralla o corta el queso en láminas, rebana el pan y corta el aguacate en láminas.",
            "Precalienta una sartén a fuego medio-alto o el grill del horno. Unta ligeramente las rebanadas de pan con aceite de oliva.",
            "Tuesta las rebanadas de pan en la sartén o grill hasta que estén doradas y crujientes por ambos lados (2–4 minutos). Si quieres, frota cada rebanada con el diente de ajo partido para aromatizar.",
            "En la misma sartén, añade 1 cucharada de aceite de oliva y sofríe el ajo picado 30 segundos hasta que desprenda aroma; añade la cebolla y cocina 2–3 minutos hasta que esté translúcida.",
            "Incorpora el jamón al sartén y saltea 2–3 minutos más hasta que tome un ligero dorado. Ajusta con sal y pimienta al gusto (ten en cuenta que el jamón puede ser salado).",
            "Mientras se cocina, mezcla el jitomate con una pizca de sal, una pizca pequeña de azúcar y una o dos cucharaditas de agua para suavizar la acidez; deja reposar 1 minuto.",
            "Monta las tostadas: sobre cada rebanada de pan tostado coloca una capa del salteado de jamón y cebolla, añade encima el jitomate preparado, luego el queso y finalmente las láminas de aguacate.",
            "Si deseas queso fundido, coloca las tostadas montadas bajo el grill 2–3 minutos hasta que el queso se derrita ligeramente.",
            "Termina con un chorrito pequeño de aceite de oliva y una última pizca de sal y pimienta al gusto. Sirve inmediatamente."
        ),
        minutes = 20,
        stars = 3,
        title = "Tostadas rústicas de jamón, aguacate y queso",
        prompt = ""
    )

    RecipeTheme {
        GeneratedRecipe(
            recipe = recipe, recipeIsSaved = false, onSave = {}
        )
    }
}