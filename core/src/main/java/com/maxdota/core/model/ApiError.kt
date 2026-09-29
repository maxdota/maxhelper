package com.maxdota.core.model

class ApiError(
  message: String,
  val code: String,
  val data: Any?
) : Throwable(message) {
  companion object {
    fun fromErrorResponse(errorData: ErrorData) = ApiError(
      message = errorData.message,
      code = errorData.code,
      data = errorData.data,
    )
  }
}