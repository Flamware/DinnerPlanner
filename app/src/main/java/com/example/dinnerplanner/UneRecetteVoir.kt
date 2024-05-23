package com.example.dinnerplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Voir(modifier: Modifier = Modifier){

    Column(modifier = modifier.fillMaxSize()
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){

            Text(
                text =" nomRecette ",
                style = MaterialTheme.typography.titleLarge,

                )
            Text(
                text =" difficulté :" + " x",
                modifier = modifier.padding(3.dp)
            )
            Text(
                text =" temps :" + " x",
                modifier = modifier.padding(3.dp)
            )

        }
        Divider (
            color = Color.Black,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        LazyColumn {
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
            item {
                Button(
                    modifier = modifier
                        .padding(start = 8.dp),
                    onClick = { }
                ) {
                    Text("Quitter")
                }
            }
        }




    }



}

@Composable
@Preview(showBackground = true)
fun PreviewVoir(){
    Voir()
}