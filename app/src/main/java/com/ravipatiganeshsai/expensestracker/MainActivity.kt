package com.ravipatiganeshsai.expensestracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpensesPointCheck(::verifySpenderStatus)
        }
    }

    private fun verifySpenderStatus(userSessionStatus: Int) {
        if (userSessionStatus == 2) {
            startActivity(Intent(this, EnterActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, ExpenseTrackerActivity::class.java))
            finish()
        }

    }
}


@Composable
fun ExpensesPointCheck(onLoginClick: (studentStatus: Int) -> Unit) {
    val context = LocalContext.current

    SideEffect {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            onLoginClick(if (SpenderDetails.getUserSession(context)) 1 else 2)
        }
    }

    ExpensesPoint()
}


@Composable
fun ExpensesPoint() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.expenses),
                contentDescription = "Expenses Tracker Image",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Expenses Tracker",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "By Ravipati Ganesh Sai",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))


        }
    }

}
