package com.maxdota.core.base

import android.content.res.Resources
import com.maxdota.core.R
import com.maxdota.core.extension.addSchedulers
import com.maxdota.core.extension.addSchedulersSync
import com.maxdota.core.model.NetworkError
import io.reactivex.Single
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseCall(
  private val resources: Resources,
) {
  fun <T> Single<T>.handleErrors(): Single<T> = this.onErrorResumeNext { throwable ->
    val error = when (throwable) {
      is UnknownHostException -> NetworkError(resources.getString(R.string.error_no_internet_connection))
      is SocketTimeoutException -> NetworkError(resources.getString(R.string.error_api_connection))
      else -> throwable
    }
    Single.error(error)
  }

  fun <T> Single<T>.proceeds(): Single<T> = this.handleErrors().addSchedulers()
  fun <T> Single<T>.proceedsSync(): Single<T> = this.handleErrors().addSchedulersSync()
}