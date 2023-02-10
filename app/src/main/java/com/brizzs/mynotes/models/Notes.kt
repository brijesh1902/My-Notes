package com.brizzs.mynotes.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    val title : String?,
    val notes : String?,
    val date : String?,
    val randomColor : Int
) : java.io.Serializable


