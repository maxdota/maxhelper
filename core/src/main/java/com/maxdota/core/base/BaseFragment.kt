package com.maxdota.core.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.maxdota.core.R
import com.maxdota.core.extension.getThrowableError
import com.maxdota.core.extension.gone
import com.maxdota.core.extension.showShortToast
import com.maxdota.core.extension.showToast
import com.maxdota.core.helper.L
import com.maxdota.core.view.ListEmptyView
import com.maxdota.core.view.LoadingView

abstract class BaseFragment<T : ViewBinding>(
  private val onBind: ((inflater: LayoutInflater, container: ViewGroup?) -> T)
) : Fragment() {
  protected var isInactive = false

  protected val handler = Handler(Looper.getMainLooper())

  private var _binding: T? = null

  // This property is only valid between onCreateView and onDestroyView.
  protected val binding get() = _binding!!

  fun getBaseActivity(): BaseActivity? {
    return activity as? BaseActivity
  }

  fun goBack() {
    L.d(FLOW_TAG, "goBack -> $this")
    activity?.onBackPressed()
  }

  fun toFragment(fragment: BaseFragment<*>) {
    L.d(FLOW_TAG, "toFragment -> $this to $fragment")
    getBaseActivity()?.toFragment(fragment)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    L.d(LIFE_CYCLE_TAG, "onCreate -> $this")
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    L.d(LIFE_CYCLE_TAG, "onCreateView -> $this")
    getBaseActivity()?.currentFragment = this
    _binding = onBind(inflater, container)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    L.d(LIFE_CYCLE_TAG, "onViewCreated -> $this")
    super.onViewCreated(view, savedInstanceState)
    isInactive = false
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    L.d(LIFE_CYCLE_TAG, "onActivityCreated -> $this")
    super.onActivityCreated(savedInstanceState)
  }

  override fun onDestroyView() {
    L.d(LIFE_CYCLE_TAG, "onDestroyView -> $this")
    isInactive = true
    super.onDestroyView()
    _binding = null
  }

  open fun recyclerView(): RecyclerView? = null
  open fun listEmptyView(): ListEmptyView? = null
  open fun viewsShownOnEmpty(): List<View> = emptyList()
  open fun viewsShowOnData(): List<View> = emptyList()
  open fun loadingView(): LoadingView? = null
  open fun loadingShimmer(): View? = null

  open fun onShowLoading() {
    loadingView()?.show()
  }

  open fun onHideLoading() {
    loadingView()?.hide()
    loadingShimmer()?.gone()
  }

  open fun onShowData(state: BaseState.ShowData) {
    onShowData(state.isListEmpty)
    recyclerView()?.isVisible = !state.isListEmpty
    listEmptyView()?.isVisible = state.isListEmpty

    viewsShownOnEmpty().forEach { it.isVisible = state.isListEmpty }
    viewsShowOnData().forEach { it.isVisible = !state.isListEmpty }
  }

  open fun onShowData(isListEmpty: Boolean) = Unit
  open fun onError() = Unit
  open fun onErrorDialogConfirmClicked(extra: String?) = Unit
  open fun onErrorDialogDismissed(extra: String?) = Unit

  // return true if the state is already handled
  // note: remember to override above methods depending on use case
  protected fun handleBaseState(state: BaseState): Boolean {
    var alreadyHandled = true
    when (state) {
      is BaseState.ShowLoadingView -> onShowLoading()
      is BaseState.HideLoadingView -> onHideLoading()
      is BaseState.NavigateBack -> goBack()
      is BaseState.ShowData -> onShowData(state)
      is BaseState.Error -> {
        onError()
        showGenericSnackBar(binding.root, state.throwable.getThrowableError(resources))
      }

      is BaseState.ErrorMessage -> {
        onError()
        showGenericSnackBar(binding.root, state.message.orEmpty())
      }

      is BaseState.Notice -> activity?.showToast(state.message)
      is BaseState.SnackBarNotice -> {
        showGenericSnackBar(binding.root, state.message)
      }

      is BaseState.ShortNotice -> activity?.showShortToast(state.message)
      else -> alreadyHandled = false
    }
    return alreadyHandled
  }

  fun showGenericSnackBar(view: View, message: String) {
    val snackBar = Snackbar.make(view, message, 3000)
    snackBar.setAction(R.string.dismiss) {
      snackBar.dismiss()
    }
    snackBar.show()
  }

  companion object {
    private const val LIFE_CYCLE_TAG = "FragmentLifecycle"
    private const val FLOW_TAG = "FragmentFlow"
  }
}