package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.HighlightBlack
import com.example.littlelemon.ui.theme.HighlightGray
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow
import com.example.littlelemon.ui.theme.SecondarySalmon

@Composable
fun Onboarding(context: Context, navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(20.dp)
                .height(40.dp)
        )
        Row(modifier = Modifier
            .background(PrimaryGreen)
            .fillMaxWidth()
            .padding(vertical = 20.dp), horizontalArrangement = Arrangement.Center) {
            Text(text = "Let's get to know you",
                modifier = Modifier.padding(vertical = 10.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = HighlightGray)
        }
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
            Text(
                text = "Personal information",
                modifier = Modifier.padding(vertical = 30.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "First name",
                style = MaterialTheme.typography.bodySmall
            )
            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                placeholder = {
                    Text(text = "Enter first name")
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Last name",
                style = MaterialTheme.typography.bodySmall
            )
            TextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                placeholder = {
                    Text(text = "Enter last name")
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Email",
                style = MaterialTheme.typography.bodySmall
            )
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = {
                    Text(text = "Enter email address")
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
        }

        Button(
            onClick = {
                      if (firstName.isBlank() && lastName.isBlank() && email.isBlank()) {
                          Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                      } else {
                          Toast.makeText(context, "Registration successful.", Toast.LENGTH_SHORT).show()
                          navController.navigate(Home.route)
                          val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)

                          sharedPreferences.edit()
                              .putBoolean("isLoggedIn", true)
                              .apply()
                      }
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            shape = RoundedCornerShape(
                12
            ),
            colors = ButtonDefaults.buttonColors(PrimaryYellow),
            border = BorderStroke(width = 1.dp,color = SecondarySalmon)

        ) {
            Text(
                text = "Register",
                color = HighlightBlack
            )
        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Onboarding(context = context, navController = navController)
}