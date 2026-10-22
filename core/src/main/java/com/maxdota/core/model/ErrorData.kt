package com.maxdota.core.model

import com.google.gson.annotations.SerializedName

data class ErrorData(
  @SerializedName("code")
  val code: String = "",
  @SerializedName("message")
  val message: String = "",
  @SerializedName("fields")
  val fields: Map<String, String> = emptyMap()
)