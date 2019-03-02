package com.haze420.android.util

import java.text.SimpleDateFormat

object Utils {
    fun convertDateString(reviewDateString: String): String{
        //      source format   "2019-02-28T14:14:33"
        //      target format  "12/23/2018"
        val sourceDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val convertedDate = sourceDateFormat.parse(reviewDateString)
        val targetDateFormat = SimpleDateFormat("MM/dd/yyyy")
        return targetDateFormat.format(convertedDate)
    }

}