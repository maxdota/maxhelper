package com.maxdota.core.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
  @SerializedName("error")
  val error: ErrorData = ErrorData()
)