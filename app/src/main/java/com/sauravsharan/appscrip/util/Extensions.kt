package com.sauravsharan.appscrip.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun getFileFromAssets(context: Context, fileName: String): String =
    context.assets.open(fileName)
        .bufferedReader().use {
            it.readText()
        }

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}