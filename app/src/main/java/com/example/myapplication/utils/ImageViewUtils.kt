package com.example.myapplication.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.utils.GeneralUtils.Companion.isImageAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun ImageView.loadCircularImage(url: String, callback: (Boolean) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val isValidUrl = isImageAvailable(url)
        withContext(Dispatchers.Main) {
            if (!isValidUrl) {
                callback(false)
                return@withContext
            }

            Glide.with(this@loadCircularImage)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(this@loadCircularImage)

            callback(true)
        }
    }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                TODO("Not yet implemented")
            }
            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                TODO("Not yet implemented")
            }
        })
        .into(this)
}