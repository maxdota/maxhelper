package com.maxdota.core.base

interface BaseState {
  data object ShowLoadingView : BaseState
  data object HideLoadingView : BaseState
  data object NavigateBack : BaseState
  data class ShowData(val isListEmpty: Boolean) : BaseState
  data class Notice(val message: String) : BaseState
  data class SnackBarNotice(val message: String) : BaseState
  data class ShortNotice(val message: String) : BaseState
  data class Success(val message: String = "") : BaseState
  data class Error(val throwable: Throwable) : BaseState
  data class ErrorMessage(val message: String?) : BaseState
  data class FieldError(val fieldErrorMap: Map<String, String>) : BaseState
}