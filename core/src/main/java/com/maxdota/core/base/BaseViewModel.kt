package com.maxdota.core.base

import androidx.lifecycle.ViewModel
import com.maxdota.core.helper.L
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : ViewModel() {
  protected val disposables = CompositeDisposable()

  open fun onDestroy() {
    L.d(TAG, "onDestroy------------------")
    disposables.clear()
  }

  protected val state by lazy { PublishSubject.create<BaseState>() }
  val stateListener: Observable<BaseState> = state

  protected open fun showLoading() {
    state.onNext(BaseState.ShowLoadingView)
  }

  protected open fun hideLoading() {
    state.onNext(BaseState.HideLoadingView)
  }

  protected open fun navigateBack() {
    state.onNext(BaseState.NavigateBack)
  }

  protected open fun showData(isListEmpty: Boolean) {
    state.onNext(BaseState.ShowData(isListEmpty))
  }

  protected open fun showNotice(message: String) {
    state.onNext(BaseState.Notice(message))
  }

  protected open fun showSnackBarNotice(message: String) {
    state.onNext(BaseState.SnackBarNotice(message))
  }

  protected open fun showShortNotice(message: String) {
    state.onNext(BaseState.ShortNotice(message))
  }

  protected open fun showError(message: String?) {
    state.onNext(BaseState.ErrorMessage(message))
  }

  protected open fun showFieldError(fieldErrorMap: Map<String, String>) {
    state.onNext(BaseState.FieldError(fieldErrorMap))
  }

  protected open fun onError(throwable: Throwable, description: CharSequence? = null) {
    val d = if (description == null) "" else "$description "
    L.e("[${this::class.java.name}]", "Error $d- ${throwable.stackTraceToString()}")
    state.onNext(BaseState.HideLoadingView)
    state.onNext(BaseState.Error(throwable))
  }

  protected open fun onErrorSilently(throwable: Throwable, description: CharSequence? = null) {
    val d = if (description == null) "" else "$description "
    L.e("[${this::class.java.name}]", "Error $d- ${throwable.stackTraceToString()}")
    state.onNext(BaseState.HideLoadingView)
  }

  protected open fun onErrorShowDialog(throwable: Throwable) {
    L.e("[${this::class.java.name}]", "Error Dialog - ${throwable.stackTraceToString()}")
    state.onNext(BaseState.HideLoadingView)
//    state.onNext(BaseState.ErrorDialog("", throwable.message))
  }

  protected open fun onErrorShowDialog(
    message: String, extra: String? = null, cancelable: Boolean = false
  ) {
    L.e("[${this::class.java.name}]", "Error Dialog - message=$message}")
    state.onNext(BaseState.HideLoadingView)
//    state.onNext(BaseState.ErrorDialog("", message, extra, cancelable))
  }

  companion object {
    private const val TAG = "BaseViewModel"
  }
}