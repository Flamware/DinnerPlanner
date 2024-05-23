package com.example.dinnerplanner

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun Index(modifier: Modifier = Modifier){

    Column {
        Text(
            text ="Lundi ",
            style = MaterialTheme.typography.titleLarge
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }

        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Text(
            text ="Mardi ",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Text(
            text ="Mercredi ",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Text(
            text ="Jeudi ",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Text(
            text ="Vendredi ",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Text(
            text ="Samedi ",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi :x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Text(
            text ="Dimanche ",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "midi : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }
        Row(modifier = modifier.border(width = 1.dp, color = Color.Black) ){
            Text(
                text = "soir : x",
                modifier = modifier.weight(2f)
            )
            Text(
                text = " nb personne",
                modifier = modifier.weight(1f)
            )
        }

        Text(
            text ="Autre ",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = " x"
        )
    }
// bouton enregistrer à rajouter mais dessus jouer avec profondeur des activités
}



@Composable
@Preview(showBackground = true)
fun PreviewIndex(){
    Index()
}

