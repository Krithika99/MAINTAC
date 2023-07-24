package com.ksas.maintac.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.ksas.maintac.R
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        val customFont = FontFamily(
            Font(R.font.happymonkeyregular)
        )

        private val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate: String = sdf.format(Date())

        fun generateListOfYears(startYear: Int, endYear: Int): List<Int> {
            return (startYear..endYear).toList()
        }
    }


}