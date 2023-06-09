package com.brizzs.mynotes.utils

import android.content.Context
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.commonmark.node.SoftLineBreak

const val DB_NAME = "note_db"

const val CURRENT_NOTE = "CURRENT_NOTE"

const val DATE_TIME_FORMAT = "EEE, d MMM yyyy HH:mm a"

fun getMarkWonBuilder(context : Context) : Markwon{
    return Markwon.builder(context)
        .usePlugin(StrikethroughPlugin.create())
        .usePlugin(TaskListPlugin.create(context))
        .usePlugin(object : AbstractMarkwonPlugin(){
            override fun configureVisitor(builder: MarkwonVisitor.Builder) {
                super.configureVisitor(builder)
                builder.on(SoftLineBreak::class.java){
                        visitor, _, ->visitor.forceNewLine()
                }
            }
        }).build()
}

fun getTimestamp() : String{
    return System.currentTimeMillis().toString()
}

