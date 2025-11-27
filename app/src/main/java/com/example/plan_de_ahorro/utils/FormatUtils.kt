package com.example.plan_de_ahorro.utils

import java.text.NumberFormat
import java.util.Locale

object FormatUtils {
    //Funcion para dar formato a las cifras monetarias
    fun formatCurrency(amount: Double): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale("es", "CO")) // Colombia usa punto para miles
        numberFormat.maximumFractionDigits = 0 // Sin decimales
        return "$" + numberFormat.format(amount)
    }
}
