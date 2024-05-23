package com.example.dinnerplanner


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button

@Composable
fun index(modifier: Modifier = Modifier){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(10.dp)
    ){
        Image(
            modifier = modifier.padding(10.dp),
            painter = painterResource(R.drawable.dinner_planner2),
            contentDescription = "dinner_planner",
        )
        Row(){
            Text(
                text = "name :",
            )
            Text (
                text = "a remplacer avec input user"
            )
        }
        Row(modifier = modifier.padding(10.dp)){

            Text(
                text = "mpd :"
            )
            Text (
                text = "a remplacer avec input user"
            )
        }
        Button(
            modifier = modifier
                .padding(start = 8.dp),
            onClick = { }
        ) {
            Text("valider")
        }

    }
}


@Composable
@Preview(showBackground = true)
fun preindex(){
    index()
}



