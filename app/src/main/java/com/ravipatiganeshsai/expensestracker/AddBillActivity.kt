package com.ravipatiganeshsai.expensestracker

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.ravipatiganeshsai.expensestracker.database.Expense
import com.ravipatiganeshsai.expensestracker.database.ExpenseDatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AddBillActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTracker()
        }
    }
}

@Composable
fun ExpenseTrackerPreview() {
    ExpenseTracker()
}

@Composable
fun ExpenseTracker() {
    val expenseTypes = listOf(
        "Tuition Fees", "Meals", "Accommodation", "Shopping",
        "Clothes", "Coffee", "Laundry Services", "Transportation", "Medicine"
    )
    var selectedExpenseType by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Add Expenses",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {


            Text(text = "Expense Type", style = MaterialTheme.typography.labelMedium)
            CustomDropdownMenu(expenseTypes, selectedExpenseType) { selectedExpenseType = it }

            Spacer(modifier = Modifier.height(16.dp))


            Text(text = "Amount", style = MaterialTheme.typography.labelMedium)
            TextField(
                value = amount,
                onValueChange = { amount = it },
                leadingIcon = { Text("£") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(text = "Select Date for Expense", style = MaterialTheme.typography.labelMedium)

            Box(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    value = selectedDate.ifEmpty { "Select Date" },
                    onValueChange = { },
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar Icon"
                        )
                    },
                    trailingIcon = {
                        Text("Pick\nDate")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { datePickerDialog.show() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    if (selectedExpenseType.isNotEmpty() && amount.isNotEmpty() && selectedDate.isNotEmpty()) {

//                        val expenseItem = Expense(
//                            0,
//                            selectedExpenseType,
//                            amount.toDouble(),
//                            selectedDate
//
//                        )

                        addExpense(context,selectedDate,selectedExpenseType,amount.toFloat())

//                        CoroutineScope(Dispatchers.IO).launch {
//                            saveExpense(context, selectedExpenseType, amount, selectedDate)
//                        }
//                        saveExpense(context,selectedExpenseType,amount, date = selectedDate)
//                        saveExpenses(context, expenseItem)
                        Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

fun addExpense(context: Context, date: String, type: String, amount: Float) {
    val dbHelper = ExpenseDatabaseHelper(context)

// Add or update an expense
    val expense = Expense(
        date = date,
        type = type,
        amount = amount
    )
    dbHelper.insertOrUpdateExpense(expense)

    // Close the database instance
//    db.close()
}




@Composable
fun CustomDropdownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Text(
            text = selectedItem.ifEmpty { "Select Expense Type" },
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                // Replace DropdownMenuItem with a direct Row or Text
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemSelected(item)
                            expanded = false
                        }
                        .padding(8.dp)
                ) {
                    Text(modifier = Modifier.fillMaxWidth(), text = item, color = Color.Black)
                }
            }
        }
    }
}




