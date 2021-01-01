package com.leafy.githubsearcher.utils

import android.content.Context
import android.widget.Toast

object Utility {
    fun getNumberOfColumn(context: Context, itemWidth: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        Toast.makeText(context, "${displayMetrics.widthPixels} / $itemWidth = ${displayMetrics.widthPixels / itemWidth}", Toast.LENGTH_SHORT)
                .show()
        return (displayMetrics.widthPixels / itemWidth).toInt()
    }
}