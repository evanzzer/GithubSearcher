package com.leafy.githubsearcher.core.utils

import android.content.Context

object Utility {
    fun getNumberOfColumn(context: Context, itemWidth: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        return (displayMetrics.widthPixels / itemWidth).toInt()
    }
}