package com.maxdota.core.model

class ApiError(
  message: String,
  val errorCode: Int,
  val code: String,
  val fields: Map<String, String>
) : Throwable(message) {
  companion object {
    fun fromErrorResponse(errorCode: Int, errorData: ErrorData) = ApiError(
      message = errorData.message,
      errorCode = errorCode,
      code = errorData.code,
      fields = errorData.fields
    )
  }
}