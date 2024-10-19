package com.zero.schooglink.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {

    fun formatDate(originalDateTime: String): String? {
        // Parse the corrected date-time string
        val zonedDateTime = ZonedDateTime.parse(originalDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"))

        // Define the output format
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a", Locale.ENGLISH)

        // Format the parsed date-time to the desired output
        return zonedDateTime.format(outputFormatter)
    }
}