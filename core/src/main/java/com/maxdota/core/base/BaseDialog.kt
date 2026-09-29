package com.maxdota.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.maxdota.core.R
import com.maxdota.core.listener.DialogListener
import com.maxdota.core.listener.GeneralListener

abstract class BaseDialog : DialogFragment() {
  protected var listener: DialogListener? = null
  protected var dialogRootView: View? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    isCancelable = isDismissable()
    dialogRootView = View.inflate(activity, getLayoutId(), null)
    setupUi()
    val dialog = AlertDialog.Builder(
      requireContext(), R.style.BaseDialogTheme
    ).setView(dialogRootView).create()
    return dialog
  }

  abstract fun getLayoutId(): Int
  open fun isDismissable() = false
  abstract fun setupUi()
}
