package com.maxdota.core.base

import android.content.res.Resources
import com.google.gson.Gson
import com.maxdota.core.R
import com.maxdota.core.extension.addSchedulers
import com.maxdota.core.extension.addSchedulersSync
import com.maxdota.core.helper.L
import com.maxdota.core.model.ApiError
import com.maxdota.core.model.ErrorResponse
import com.maxdota.core.model.NetworkError
import io.reactivex.Single
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertPathValidatorException
import javax.net.ssl.SSLHandshakeException

open class BaseCall(
  private val resources: Resources,
  private val gson: Gson,
) {
  fun <T> Single<T>.handleErrors(
    onApiError: (error: ApiError) -> Unit
  ): Single<T> = this.onErrorResumeNext { throwable ->
    val error = when (throwable) {
      is UnknownHostException -> NetworkError(resources.getString(R.string.error_no_internet_connection))
      is SocketTimeoutException,
      is SSLHandshakeException -> NetworkError(resources.getString(R.string.error_api_connection))

      is HttpException -> {
        var result = throwable
        throwable.response()?.let { response ->
          val responseCode = response.code()
          val responseError = response.errorBody()?.string().orEmpty()
          if (responseCode == 400 || responseCode == 422) {
            try {
              val errorResponse = gson.fromJson(responseError, ErrorResponse::class.java)
              result = ApiError.fromErrorResponse(responseCode, errorResponse.error)
              onApiError(result)
            } catch (exception: Exception) {
              result = exception
            }
          }
        }
        result
      }

      else -> throwable
    }
    Single.error(error)
  }

  fun <T> Single<T>.proceeds(
    onApiError: (error: ApiError) -> Unit
  ): Single<T> = this.handleErrors(onApiError).addSchedulers()

  fun <T> Single<T>.proceedsSync(
    onApiError: (error: ApiError) -> Unit
  )
    : Single<T> = this.handleErrors(onApiError).addSchedulersSync()
}