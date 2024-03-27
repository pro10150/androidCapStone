package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
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
import com.example.littlelemon.ui.theme.PrimaryYellow
import com.example.littlelemon.ui.theme.SecondarySalmon

@Composable
fun Profile(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var firstName by remember {
        mutableStateOf(
            sharedPreferences.getString("firstName", "")
        )
    }
    var lastName by remember {
        mutableStateOf(
            sharedPreferences.getString("lastName", "")
        )
    }
    var email by remember {
        mutableStateOf(
            sharedPreferences.getString("email", "")
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(20.dp)
                .height(40.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image"
        )
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp), horizontalAlignment = Alignment.Start) {
            Text(
                text = "Personal information",
                modifier = Modifier.padding(vertical = 30.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "First name",
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedTextField(
                value = firstName!!,
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
            OutlinedTextField(
                value = lastName!!,
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
            OutlinedTextField(
                value = email!!,
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
            Button(
                onClick = {
                    sharedPreferences
                              .edit()
                              .remove("firstName")
                              .remove("lastName")
                              .remove("email")
                              .remove("isLoggedIn")
                              .apply()
                    navController.navigate(Onboarding.route)
                          },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, SecondarySalmon),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(PrimaryYellow)
            ) {
                Text(
                    text = "Log out",
                    color = HighlightBlack
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    val context = LocalContext.current
    val navController = rememberNavController()
    Profile(context = context, navController = navController)
}