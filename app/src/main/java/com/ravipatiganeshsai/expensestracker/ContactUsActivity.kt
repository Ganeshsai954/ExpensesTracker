package com.ravipatiganeshsai.expensestracker

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ContactUsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            if (SummaryType.usType == 1) {
                ContactUsScreen()
            } else {
                AboutUs()
            }
        }
    }
}


@Composable
fun AboutUs() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        (context as Activity).finish()
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "back"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "About Us",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "The Ganesh Sairavipati Expense Tracker mobile app is a tool designed to help students track their expenses and manage their finances. This type of app is useful for students who want to keep track of their daily spending, set budgets, and analyze their financial habits.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ContactUsScreen(
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        (context as Activity).finish()
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "back"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

        }

        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {


            Text(
                text = "Expenses Tracker by Ravipati Ganesh Sai",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "For any concerns, please enter below and submit.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Full Name Input
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Email Address Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )

            // Subject Input
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                modifier = Modifier.fillMaxWidth()
            )

            // Message Input
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Enter Message") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5,
                singleLine = false
            )

            // Submit Button
            Button(
                onClick = {
                    onSubmit(fullName, email, subject, message, context)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Submit")
            }

        }
    }
}


fun onSubmit(fullName: String, email: String, subject: String, message: String, context: Context) {
    if (fullName.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
        Toast.makeText(context, "Please Fill all details", Toast.LENGTH_SHORT).show()
    } else {
        //call firebase function
    }
}

