import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.R
import com.example.dinnerplanner.data.local.dao.UserDao
import com.example.dinnerplanner.data.local.entity.User

@Composable
fun LoginScreen(
    modifier: Modifier,
    onLoginResult: (Boolean) -> Unit // Lambda function to report login result
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(R.drawable.dinner_planner2),
        contentDescription = "dinner_planner",
        modifier = modifier
            .clip(CircleShape)
            .size(200.dp) // Adjust size as needed
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Simulate authentication
                val loggedIn = authenticateUser(email, password)
                // Report login result
                onLoginResult(loggedIn)
                // Show error if login failed
                showError = !loggedIn
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
        // Display error message if login failed
        if (showError) {
            Text(
                text = "Incorrect email or password",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(modifier = Modifier, onLoginResult = {})
}

private fun authenticateUser(email: String, password: String): Boolean {
    // Use userdao to authenticate

