package com.claudiogalvaodev.camaraaberta.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openYoutube(videoId: String) {
    val appIntent = Intent(
        Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId")
    )
    val webIntent = Intent(
        Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId")
    )
    try {
        startActivity(appIntent)
    } catch (e: Exception) {
        startActivity(webIntent)
    }
}