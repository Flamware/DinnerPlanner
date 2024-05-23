package com.example.dinnerplanner

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListeIngredient(modifier: Modifier = Modifier){


    LazyColumn {
        item {
            Row{
                Text(
                    text = " ingredient",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = modifier.weight(2f)
                )

                Text(
                    text = "quantite",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = modifier.weight(1f)

                )
            }
            Divider (
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )

        }
        items(30){

            Row{
                Text(
                    text = " ingredient",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.weight(2f)
                )

                Divider (
                    color = Color.Black,
                    modifier = Modifier
                        .width(1.dp)
                        .height(30.dp)

                )

                Text(
                    text = " quantite",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.weight(1f)

                )
            }

        }

        }

}



@Composable
@Preview(showBackground = true)
fun PreviewlisteIngredient(){
    ListeIngredient()
}