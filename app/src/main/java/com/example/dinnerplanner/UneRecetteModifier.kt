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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun  ModifierR(modifier: Modifier = Modifier){

    Column(modifier = modifier.fillMaxSize()
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){

            Text(
                text =" inputRecette ",
                style = MaterialTheme.typography.titleLarge,

                )
            Text(
                text =" difficulté :" + " liste deroulante",
                modifier = modifier.padding(3.dp)
            )
            Text(
                text =" temps :" + " input user",
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

                Row(verticalAlignment = Alignment.CenterVertically,){
                    Text(
                        text = " input user",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.weight(2f)
                    )

                    Divider (
                        color = Color.Black,
                        modifier = Modifier
                            .width(1.dp)
                            .height(48.dp)

                    )

                    Text(
                        text = " input user",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.weight(1f)

                    )
                    Button(
                        modifier = modifier
                            .padding(start = 8.dp),
                        onClick = { }
                    ) {
                        Text("-")
                    }
                }

            }
            item {
                Button(
                    modifier = modifier
                        .padding(start = 8.dp),
                    onClick = { }
                ) {
                    Text(text = "+",
                        textAlign = TextAlign.Center)
                }
            }
        }



// bouton enregistrer à rajouter mais dessus jouer avec profondeur des activités
    }



}

@Composable
@Preview(showBackground = true)
fun PreviewModifier(){
    ModifierR()
}