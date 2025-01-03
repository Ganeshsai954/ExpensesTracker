package com.ravipatiganeshsai.expensestracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey



data class Expense(
    var entityId : Int = 0,
    var date : String = "",
    var type : String = "",
    var amount : Float = 0f
)