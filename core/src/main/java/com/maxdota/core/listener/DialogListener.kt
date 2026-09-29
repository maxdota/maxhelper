package com.maxdota.core.listener

import com.maxdota.core.base.BaseDialog

interface DialogListener {
  fun onAction(dialog: BaseDialog)
}