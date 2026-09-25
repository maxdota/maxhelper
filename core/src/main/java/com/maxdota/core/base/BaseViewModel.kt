package com.maxdota.core.base

import androidx.lifecycle.ViewModel
import com.maxdota.core.helper.L
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : ViewModel() {
  protected val compositeDisposable = CompositeDisposable()

  fun onDestroy() {
    L.d(TAG, "onDestroy------------------")
    compositeDisposable.clear()
  }

  fun Disposable.addToComposite() {
    compositeDisposable.add(this)
  }

  protected val state by lazy { PublishSubject.create<BaseState>() }
  val stateListener: Observable<BaseState> = state

  protected fun showLoading() {
    state.onNext(BaseState.ShowLoadingView)
  }

  protected fun hideLoading() {
    state.onNext(BaseState.HideLoadingView)
  }

  protected fun navigateBack() {
    state.onNext(BaseState.NavigateBack)
  }

  protected fun showData(isListEmpty: Boolean) {
    state.onNext(BaseState.ShowData(isListEmpty))
  }

  protected fun showNotice(message: String) {
    state.onNext(BaseState.Notice(message))
  }

  protected fun showSnackBarNotice(message: String) {
    state.onNext(BaseState.SnackBarNotice(message))
  }

  protected fun showShortNotice(message: String) {
    state.onNext(BaseState.ShortNotice(message))
  }

  protected fun showError(message: String?) {
    state.onNext(BaseState.ErrorMessage(message))
  }

  protected fun showFieldError(fieldErrorMap: Map<String, String>) {
    state.onNext(BaseState.FieldError(fieldErrorMap))
  }

  protected fun onError(throwable: Throwable, description: CharSequence? = null) {
    val d = if (description == null) "" else "$description "
    L.e("[${this::class.java.name}]", "Error $d- ${throwable.stackTraceToString()}")
    state.onNext(BaseState.HideLoadingView)
    state.onNext(BaseState.Error(throwable))
  }

  protected fun onErrorSilently(throwable: Throwable, description: CharSequence? = null) {
    val d = if (description == null) "" else "$description "
    L.e("[${this::class.java.name}]", "Error $d- ${throwable.stackTraceToString()}")
    state.onNext(BaseState.HideLoadingView)
  }

  protected fun onErrorShowDialog(throwable: Throwable) {
    L.e("[${this::class.java.name}]", "Error Dialog - ${throwable.stackTraceToString()}")
    state.onNext(BaseState.HideLoadingView)
//    state.onNext(BaseState.ErrorDialog("", throwable.message))
  }

  protected fun onErrorShowDialog(
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