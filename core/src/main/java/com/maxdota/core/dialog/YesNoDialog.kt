package com.maxdota.core.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.maxdota.core.R
import com.maxdota.core.base.BaseDialog
import com.maxdota.core.extension.setOnThrottleClickListener
import com.maxdota.core.extension.visible
import com.maxdota.core.listener.DialogListener

class YesNoDialog : BaseDialog() {
  private val textMessage by lazy { dialogRootView?.findViewById<TextView>(R.id.textMessage) }
  private val divider by lazy { dialogRootView?.findViewById<View>(R.id.divider) }
  private val buttonAction by lazy { dialogRootView?.findViewById<Button>(R.id.buttonAction) }
  private val buttonCancel by lazy { dialogRootView?.findViewById<Button>(R.id.buttonCancel) }

  override fun getLayoutId() = R.layout.dialog_yes_no

  override fun setupUi() {
    val message = arguments?.getString(KEY_MESSAGE)
    val button = arguments?.getString(KEY_BUTTON)

    textMessage?.text = message
    divider?.visible()
    buttonAction?.visible()
    buttonAction?.text = button
    buttonAction?.setOnThrottleClickListener {
      dismiss()
      listener?.onAction(this)
    }
    buttonCancel?.setOnThrottleClickListener {
      dismiss()
    }
  }

  companion object {
    private const val KEY_MESSAGE = "KEY_MESSAGE"
    private const val KEY_BUTTON = "KEY_BUTTON"

    fun newInstance(
      message: String, button: String, listener: DialogListener? = null
    ): YesNoDialog {
      val dialog = YesNoDialog()
      val arguments = Bundle().apply {
        putString(KEY_MESSAGE, message)
        putString(KEY_BUTTON, button)
      }
      dialog.arguments = arguments
      dialog.listener = listener
      return dialog
    }
  }
}
