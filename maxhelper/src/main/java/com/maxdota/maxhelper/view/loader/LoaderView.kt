package com.maxdota.maxhelper.view.loader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.maxdota.maxhelper.R
import com.maxdota.maxhelper.view.loader.LoaderState.Companion.STATE_ERROR
import com.maxdota.maxhelper.view.loader.LoaderState.Companion.STATE_LOADING

class LoaderView(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    init {
        inflate(context, R.layout.view_loader, this)
        onInit()
    }

    private lateinit var containerError: View
    private lateinit var buttonRetry: View
    private lateinit var buttonClose: View
    private lateinit var viewLoading: LoadingView
    private lateinit var tvError: TextView

    private fun onInit() {
        containerError = findViewById(R.id.containerError)
        buttonRetry = findViewById(R.id.buttonRetry)
        buttonClose = findViewById(R.id.buttonClose)
        viewLoading = findViewById(R.id.viewLoading)
        tvError = findViewById(R.id.tvError)

        buttonClose.setOnClickListener { finishLoading() }
    }

    fun setOnRetryListener(onRetryClicked: OnClickListener) {
        buttonRetry.setOnClickListener(onRetryClicked)
    }

    fun setState(state: LoaderState) {
        when (state.state) {
            STATE_LOADING -> setLoading()
            STATE_ERROR -> setError(state.errorMessage)
            else -> finishLoading()
        }
    }

    fun setLoading() {
        viewLoading.triggerVisibility(true)
        visibility = VISIBLE
    }

    fun finishLoading() {
        viewLoading.triggerVisibility(false)
        visibility = GONE
    }

    private fun setError(errorText: String?) {
        tvError.text = errorText ?: ""
        containerError.visibility = VISIBLE
        viewLoading.visibility = GONE
        visibility = VISIBLE
    }
}