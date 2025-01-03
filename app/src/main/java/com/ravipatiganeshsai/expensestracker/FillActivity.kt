package com.ravipatiganeshsai.expensestracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

class FillActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FillActivityScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FillActivityScreenP() {
    FillActivityScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillActivityScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var Lastname by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set the outer background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Section with Back Arrow and Sign Up
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)

                    .padding(16.dp), // Padding around the row
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = "Sign Up",
                        fontWeight = FontWeight.Bold,

                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            }

            // Login Form Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 32.dp,

                            )
                    ) // Apply rounded corners
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))


                    Spacer(modifier = Modifier.height(24.dp))

//                    OutlinedTextField(
//                        value = firstName,
//                        onValueChange = { firstName = it },
//                        label = { Text("Full Name") },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            containerColor = Color.LightGray
//                        )
//                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,
                            focusedContainerColor = Color.LightGray,
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )


                    // Last Name TextField
//                    OutlinedTextField(
//                        value = Lastname,
//                        onValueChange = { Lastname = it },
//                        label = { Text("Last Name") },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            containerColor = Color.LightGray
//                        )
//                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        value = Lastname,
                        onValueChange = { Lastname = it },
                        label = { Text("Last Name") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,
                            focusedContainerColor = Color.LightGray,
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )


                    // Email TextField
//                    OutlinedTextField(
//                        value = email,
//                        onValueChange = { email = it },
//                        label = { Text("Email") },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            containerColor = Color.LightGray
//                        )
//                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,
                            focusedContainerColor = Color.LightGray,
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )


                    // Password TextField
                    var passwordVisible by remember { mutableStateOf(false) }
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = { password = it },
//                        label = { Text("Password") },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            containerColor = Color.LightGray
//                        ),
//                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                        trailingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_password_24),
//                                contentDescription = "Toggle Password Visibility",
//                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
//                            )
//                        }
//                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,
                            focusedContainerColor = Color.LightGray,
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )


//                    OutlinedTextField(
//                        value = confirmPassword,
//                        onValueChange = { confirmPassword = it },
//                        label = { Text("Confirm Password") },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            containerColor = Color.LightGray
//                        ),
//                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                        trailingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_password_24),
//                                contentDescription = "Toggle Password Visibility",
//                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
//                            )
//                        }
//                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,
                            focusedContainerColor = Color.LightGray,
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    Button(
                        onClick = {
                            when {
                                firstName.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please enter your full name",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                Lastname.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please enter your last name",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                                email.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please enter your email",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                                password.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please enter your password",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                                (password != confirmPassword) -> {
                                    Toast.makeText(
                                        context,
                                        "Passwords doesn't match",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                                else -> {

                                    val spenderData = SpenderData(
                                        firstName,
                                        Lastname,
                                        email,
                                        password
                                    )


                                    try {
                                        FirebaseDatabase.getInstance()
                                            .getReference("SpenderDetails")
                                            .child(spenderData.email.replace(".", ","))
                                            .setValue(spenderData)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Toast.makeText(
                                                        context,
                                                        "Now you can track your expense easily, Start Now",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                    context.startActivity(
                                                        Intent(
                                                            context,
                                                            EnterActivity::class.java
                                                        )
                                                    )
                                                    (context as Activity).finish()

                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Something went wrong",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            .addOnFailureListener { _ ->
                                                Log.e("Test", "Something went wrong")
                                            }
                                    } catch (e: Exception) {
                                        Log.e("Test", e.message.toString())
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(text = "Sign Up", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Footer Text
                    Row {
                        Text(text = "Already have an account? ", color = Color.Black)
                        Text(
                            text = "Login",
                            color = Color.Blue,
                            modifier = Modifier.clickable {
                                context.startActivity(Intent(context, EnterActivity::class.java))
                                (context as Activity).finish()
                            }
                        )
                    }

                }
            }
        }


    }


}

