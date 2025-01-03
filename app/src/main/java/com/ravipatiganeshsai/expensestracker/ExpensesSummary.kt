package com.ravipatiganeshsai.expensestracker

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ravipatiganeshsai.expensestracker.database.Expense
import com.ravipatiganeshsai.expensestracker.database.ExpenseDatabaseHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class ExpensesSummary : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            if (SummaryType.click == 1) {
                ExpensesSummaryDesign()
            } else {
                ExpenseTrackerWithBarGraph(ExpenseDatabaseHelper(this))
            }
        }
    }
}

@Composable
fun ExpensesSummaryDesign() {
    val context = LocalContext.current

    var selectedDate by remember { mutableStateOf("") }
    val expenses = remember { mutableStateOf<List<Expense>>(emptyList()) }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"

            expenses.value = getDailyExpenseSQL(context, selectedDate)

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
                .background(Color.Black)
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        (context as? Activity)?.finish()
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "back"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Daily Expenses",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = "Select Date to see Expenses",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = selectedDate.ifEmpty { "Select Date" },
                    onValueChange = {},
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar Icon"
                        )
                    },
                    trailingIcon = {
                        Text(
                            text = "Pick\nDate",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
                        )
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

            Spacer(modifier = Modifier.height(16.dp))

            if (expenses.value.isNotEmpty()) {
                ExpenseBarGraph(expenses = expenses.value)

                val maxExpense = expenses.value.maxByOrNull { it.amount }

                if (maxExpense != null) {
                    Text(
                        text = "Maximum Expense",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "${maxExpense.type} on ${maxExpense.date} with ₹${maxExpense.amount}",
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Text(
                        text = "No expenses available",
                        modifier = Modifier.padding(8.dp)
                    )
                }

            } else {
                Text(
                    text = "No expenses found for the selected date.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun ExpenseBarGraph(expenses: List<Expense>) {
    val maxAmount = expenses.maxOfOrNull { it.amount } ?: 1f
    val barWidth = 60f

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val barSpacing = (canvasWidth - (expenses.size * barWidth)) / (expenses.size + 1)

        expenses.forEachIndexed { index, expense ->
            val x = barSpacing + index * (barWidth + barSpacing)
            val barHeight = (expense.amount / maxAmount) * canvasHeight
            val y = canvasHeight - barHeight

            // Draw bar
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight)
            )

            // Draw amount above bar
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    expense.amount.toString(),
                    x + barWidth / 4,
                    y - 8,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 24f
                        textAlign = android.graphics.Paint.Align.LEFT
                    }
                )
            }

            // Draw expense type below bar
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    expense.type,
                    x + barWidth / 4,
                    canvasHeight + 20,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 20f
                        textAlign = android.graphics.Paint.Align.LEFT
                    }
                )
            }
        }
    }
}

fun getDailyExpenseSQL(context: Context, date: String): List<Expense> {
    val dbHelper = ExpenseDatabaseHelper(context)
    val dailyExpenses = dbHelper.getDailyExpenses(date)


    return dailyExpenses
}

//---------------Monthly Expenses Summary----------------------------------------

@Composable
fun ExpenseTrackerWithBarGraph(dbHelper: ExpenseDatabaseHelper) {
    val currentDate = LocalDate.now()
    val lastThreeMonths = (0..2).map {
        currentDate.minusMonths(it.toLong()).format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }.reversed() // Reverse to get in chronological order
    val selectedMonth = remember { mutableStateOf(lastThreeMonths.last()) }
    val monthlyExpenses = remember { mutableStateOf<List<Expense>>(emptyList()) }

    val context = LocalContext.current

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
                text = "Monthly Summary",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

        }

        Column(modifier =Modifier.padding(12.dp)) {


            // Dropdown Menu for selecting a month
            CustomDropdownMenu(
                items = lastThreeMonths,
                selectedItem = selectedMonth.value,
                onItemSelected = { month ->
                    selectedMonth.value = month
                    val monthYear = LocalDate.parse(
                        "01 $month",
                        DateTimeFormatter.ofPattern("dd MMMM yyyy")
                    ).format(DateTimeFormatter.ofPattern("MM/yyyy"))

                    // Fetch expenses for the selected month
                    monthlyExpenses.value = dbHelper.getMonthlyExpenses(monthYear)

                    Log.e("Test", "List Size : ${monthlyExpenses.value.size}")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bar Graph for monthly expenses
            if (monthlyExpenses.value.isNotEmpty()) {
                MonthlyExpensesBarGraph(expenses = monthlyExpenses.value)
            } else {
                Text(
                    "No expenses recorded for this month.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Text showing the day with maximum expense
            if (monthlyExpenses.value.isNotEmpty()) {
                val maxExpenseDay = calculateMaxExpenseDay(monthlyExpenses.value)
                Text(
                    text = "You Spent Maximum Money on $maxExpenseDay.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MonthlyExpensesBarGraph(expenses: List<Expense>) {
    val groupedExpenses = expenses.groupBy { it.date }
    val dayWiseTotals = groupedExpenses.mapValues { entry ->
        entry.value.sumOf { it.amount.toDouble() }
    }

    val maxAmount = dayWiseTotals.values.maxOrNull() ?: 0.0
    val days = dayWiseTotals.keys.sortedBy {
        LocalDate.parse(
            it,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )
    }
    val amounts = days.map { dayWiseTotals[it]?.toFloat() ?: 0f }

    // Bar graph
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = String.format("%.0f", amounts[index]),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .weight(1f)
                )

                Box(
                    modifier = Modifier
                        .height((amounts[index] / maxAmount * 150).dp) // Scale the bar height
                        .width(20.dp)
                        .background(Color.Blue)
                )
                Text(
                    text = days[index].split("/")[0],
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}

fun calculateMaxExpenseDay(expenses: List<Expense>): String {
    val groupedExpenses = expenses.groupBy { it.date }
    val dayWiseTotals = groupedExpenses.mapValues { entry ->
        entry.value.sumOf { it.amount.toDouble() } // Explicitly use Double for summation
    }
    return dayWiseTotals.maxByOrNull { it.value }?.key ?: "N/A"
}

