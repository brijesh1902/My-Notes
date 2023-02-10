package com.brizzs.mynotes.utils

const val DB_NAME = "note_db"

const val CURRENT_NOTE = "CURRENT_NOTE"

const val DATE_TIME_FORMAT = "EEE, d MMM yyyy HH:mm a"

fun getTimestamp() : String{
    return System.currentTimeMillis().toString()
}

