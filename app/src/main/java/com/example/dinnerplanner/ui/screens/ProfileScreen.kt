import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.data.local.database.entity.User

@Composable
fun ProfileScreen(navController: NavController, viewModel: DinnerPlannerViewModel) {
    val authViewModel = viewModel.authViewModel

    val currentUser by viewModel.authViewModel.currentUser.observeAsState()

    // Create a launcher for the GetContent contract
    val getContent = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        // Handle the returned URI
        println("Selected image URI: $uri")
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Profile Screen", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            currentUser?.let { user ->
                Text(text = "Username: ${user.username}", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = { getContent.launch("image/*") }) {
                Text("Import Photo")
            }
            Button(onClick = {
                // Handle logout
                authViewModel.logout()
                navController.navigate("login")
            }) {
                Text(text = "Logout")
            }
        }
    }
}