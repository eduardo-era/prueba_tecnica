package com.example.myapplication.utils

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy { bindingInflater.invoke(layoutInflater) }


@Suppress("DEPRECATION")
internal fun Activity.changeStatusBarColor(colorResource: Int, isLight: Boolean) {
    this.window.statusBarColor = ContextCompat.getColor(this.applicationContext, colorResource)
    WindowInsetsControllerCompat(this.window, this.window.decorView).isAppearanceLightStatusBars = isLight
}