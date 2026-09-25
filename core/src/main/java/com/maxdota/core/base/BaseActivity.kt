package com.maxdota.core.base

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.maxdota.core.R
import com.maxdota.core.helper.L

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId) {
  var currentFragment: BaseFragment<*>? = null
  var backAction: String? = null

  abstract fun fragment(): BaseFragment<*>

  fun toFragment(fragment: BaseFragment<*>) {
    L.d(TAG, "toFragment: ${fragment.javaClass.simpleName}")
    backAction = null
    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container_view, fragment)
      .addToBackStack(fragment.javaClass.simpleName)
      .commit()
  }

  fun popToFragment(name: String, action: String? = null) {
    L.d(TAG, "popToFragment: $name")
    backAction = action
    supportFragmentManager.popBackStack(name, 0)
  }

  fun showKeyboard(view: View) {
    (getSystemService("input_method") as InputMethodManager).showSoftInput(view, 0)
  }

  protected fun setPaddingForEdgeToEdge(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { view, insets ->
      val systemBars = insets.getInsets(
        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
      )
      view.setPadding(
        systemBars.left, systemBars.top, systemBars.right, systemBars.bottom
      )
      insets
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setPaddingForEdgeToEdge(findViewById(R.id.fragment_container_view))

    if (savedInstanceState == null) {
      toFragment(fragment())
    }
  }

  companion object {
    private const val TAG = "BaseActivity"
  }
}