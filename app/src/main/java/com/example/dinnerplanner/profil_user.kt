package com.example.dinnerplanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Profil(modifier: Modifier = Modifier){

    var boolModeSombre  by remember { mutableStateOf(true) }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
        ) {

        Image(
            painter = painterResource(R.drawable.dinner_planner2),
            contentDescription = "dinner_planner",
            modifier = modifier.clip(CircleShape)
                .background(color = Color.Black)
        )
        Row(modifier = modifier.padding(10.dp)){
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
        Row(modifier = modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically){

            Text(
                text = "Mode sombre : "
            )
            Switch(
                checked = boolModeSombre,
                onCheckedChange = {
                    boolModeSombre = it
                }
            )
        }


    }


}

@Composable
@Preview(showBackground = true)
fun Previewprofil(){
    Profil()
}


