package com.maxdota.core.extension

import android.content.res.Resources
import com.maxdota.core.R
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.getThrowableError(resources: Resources): String {
  when (this) {
    is HttpException -> {
      response()?.errorBody()?.let {
        return getErrorMessage(it)
      }
    }

    is UnknownHostException -> return resources.getString(R.string.error_no_internet_connection)
    is SocketTimeoutException -> return this.message!!
    is IOException -> return this.message!!
    else -> return this.message ?: ""
  }
  return ""
}

private fun getErrorMessage(responseBody: ResponseBody): String {
  return try {
    responseBody.string()
  } catch (e: Exception) {
    e.message.orEmpty()
  }
}