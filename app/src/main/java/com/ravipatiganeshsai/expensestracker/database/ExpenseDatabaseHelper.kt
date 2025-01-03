package com.ravipatiganeshsai.expensestracker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Expenses.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_EXPENSES = "expenses"
        const val COLUMN_ID = "entityId"
        const val COLUMN_DATE = "date"
        const val COLUMN_TYPE = "type"
        const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_EXPENSES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    // Insert or Update Expense
    fun insertOrUpdateExpense(expense: Expense) {
        val db = writableDatabase

        // Check if an expense with the same type and date exists
        val query = """
            SELECT * FROM $TABLE_EXPENSES 
            WHERE $COLUMN_DATE = ? AND $COLUMN_TYPE = ?
        """
        val cursor = db.rawQuery(query, arrayOf(expense.date, expense.type))

        if (cursor.moveToFirst()) {
            // If the expense exists, update the amount
            val existingAmount = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
            val newAmount = existingAmount + expense.amount

            val contentValues = ContentValues().apply {
                put(COLUMN_AMOUNT, newAmount)
            }
            db.update(
                TABLE_EXPENSES,
                contentValues,
                "$COLUMN_DATE = ? AND $COLUMN_TYPE = ?",
                arrayOf(expense.date, expense.type)
            )
        } else {
            // If the expense does not exist, insert a new record
            val contentValues = ContentValues().apply {
                put(COLUMN_DATE, expense.date)
                put(COLUMN_TYPE, expense.type)
                put(COLUMN_AMOUNT, expense.amount)
            }
            db.insert(TABLE_EXPENSES, null, contentValues)
        }

        cursor.close()
        db.close()
    }

    // Fetch Daily Expenses
    fun getDailyExpenses(date: String): List<Expense> {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_EXPENSES WHERE $COLUMN_DATE = ?"
        val cursor = db.rawQuery(query, arrayOf(date))

        val expenses = mutableListOf<Expense>()
        while (cursor.moveToNext()) {
            expenses.add(
                Expense(
                    entityId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    amount = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                )
            )
        }

        cursor.close()
        db.close()
        return expenses
    }

    fun getMonthlyExpenses(monthYear: String): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val db = this.readableDatabase

        // Extract month and year for comparison
        val query = """
        SELECT * FROM $TABLE_EXPENSES 
        WHERE substr($COLUMN_DATE, 4, 7) = ?
    """
        val cursor = db.rawQuery(query, arrayOf(monthYear)) // monthYear should be in "MM/YYYY"

        while (cursor.moveToNext()) {
            val expense = Expense(
                entityId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                amount = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
            )
            expenses.add(expense)
        }

        cursor.close()
        db.close()
        return expenses
    }




    fun getWeeklyExpenses(startDate: String, endDate: String): List<Expense> {
        val db = readableDatabase
        val query = """
        SELECT * FROM $TABLE_EXPENSES 
        WHERE $COLUMN_DATE BETWEEN ? AND ?
    """
        val cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        val expenses = mutableListOf<Expense>()
        while (cursor.moveToNext()) {
            expenses.add(
                Expense(
                    entityId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    amount = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                )
            )
        }

        cursor.close()
        db.close()
        return expenses
    }

}
