package com.example.foodcoma.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openYoutube(ctx: Context, url: String) {
    val link = Uri.parse(url)
    val youtubeIntent = Intent(Intent.ACTION_VIEW, link)
    ctx.startActivity(youtubeIntent)
}