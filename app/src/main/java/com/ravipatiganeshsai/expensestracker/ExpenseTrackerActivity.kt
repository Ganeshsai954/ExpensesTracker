package com.ravipatiganeshsai.expensestracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ExpenseTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerScreen()
        }
    }
}

@Composable
fun ExpenseTrackerScreen() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
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
                    .size(32.dp),
                painter = painterResource(id = R.drawable.expenses),
                contentDescription = "back"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Expenses Tracker",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                            context.startActivity(Intent(context, AddBillActivity::class.java))
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.enter_expenses),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Enter\nExpenses",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                            SummaryType.click = 1
                            context.startActivity(Intent(context, ExpensesSummary::class.java))
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.expense),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))


                    Text(
                        text = "Expenses\nSummary",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                            SummaryType.click = 2
                            context.startActivity(Intent(context, ExpensesSummary::class.java))

                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.monthly_expenses),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Monthly\nSummary",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                            SummaryType.usType = 1
                            context.startActivity(Intent(context, ContactUsActivity::class.java))

                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.contactus),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Contact Us",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                            SummaryType.usType = 2
                            context.startActivity(Intent(context, ContactUsActivity::class.java))

                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.about_us),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))


                    Text(
                        text = "About Us",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                            logout(context)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Logout",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

fun logout(context: Context) {
    SpenderDetails.storeUserSession(context, false)
    val intent = Intent(context, EnterActivity::class.java)
    intent.flags =
        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
    (context as Activity).finish()
}

object SummaryType {
    var click = 0
    var usType = 0
}

object SpenderDetails {

    fun storeUserSession(context: Context, value: Boolean) {
        val studentStatus = context.getSharedPreferences("EXPENSES_TRACKER", Context.MODE_PRIVATE)
        val editor = studentStatus.edit()
        editor.putBoolean("SPENDER_SESSION", value).apply()
    }

    fun getUserSession(context: Context): Boolean {
        val studentStatus = context.getSharedPreferences("EXPENSES_TRACKER", Context.MODE_PRIVATE)
        return studentStatus.getBoolean("SPENDER_SESSION", false)
    }

    fun storeSpenderMail(context: Context, value: String) {
        val studentMail = context.getSharedPreferences("EXPENSES_TRACKER", Context.MODE_PRIVATE)
        val editor = studentMail.edit()
        editor.putString("SPENDER_MAIL", value).apply()
    }

    fun getSpenderMail(context: Context): String {
        val studentMail = context.getSharedPreferences("EXPENSES_TRACKER", Context.MODE_PRIVATE)
        return studentMail.getString("SPENDER_MAIL", "")!!
    }

    fun storeSpenderName(context: Context, value: String) {
        val studentName = context.getSharedPreferences("EXPENSES_TRACKER", Context.MODE_PRIVATE)
        val editor = studentName.edit()
        editor.putString("SPENDER_NAME", value).apply()
    }

    fun getSpenderName(context: Context): String {
        val studentName = context.getSharedPreferences("EXPENSES_TRACKER", Context.MODE_PRIVATE)
        return studentName.getString("SPENDER_NAME", "")!!
    }

}