package com.example.dinnerplanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource


@Composable
fun ListeRecette(modifier: Modifier = Modifier){

    val recetteModifier = modifier.border(width = 2.dp,
        color = Color.Black).fillMaxSize().height(120.dp)

    val boutonModifier = modifier.padding(3.dp)

   LazyColumn {
        items(30){
            Row(modifier = recetteModifier,
                verticalAlignment = Alignment.CenterVertically,
                ){
                Column{
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

                Button(
                    modifier = boutonModifier,
                    onClick = { }){
                        Text(text ="modifier",
                        style = MaterialTheme.typography.labelSmall,
                            )
                    }
                Button(
                    modifier = boutonModifier,
                    onClick = { }
                ) {
                    Text(text ="voir",
                        style = MaterialTheme.typography.labelSmall,)
                }
                Image(
                    painter = painterResource(R.drawable.dinner_planner2),
                    contentDescription = "dinner_planner",
                    modifier = Modifier.height(100.dp).width(100.dp)
                )

    }

        }
    }



}


@Composable
@Preview(showBackground = true)
fun PreviewlisteRecette(){
    ListeRecette()
}