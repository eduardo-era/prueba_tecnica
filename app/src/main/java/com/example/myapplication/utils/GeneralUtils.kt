package com.example.myapplication.utils

import java.net.HttpURLConnection
import java.net.URL

class GeneralUtils {
    companion object {
        fun isImageAvailable(urlString: String): Boolean {
            return try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "HEAD"
                connection.connectTimeout = 300
                connection.readTimeout = 300
                val responseCode = connection.responseCode
                connection.disconnect()
                val ret = responseCode == HttpURLConnection.HTTP_OK
                ret

            } catch (e: Exception) {
                false
            }
        }
    }
}