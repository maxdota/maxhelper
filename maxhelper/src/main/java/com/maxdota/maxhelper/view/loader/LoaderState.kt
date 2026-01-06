package com.maxdota.maxhelper.view.loader

data class LoaderState(
    val state: Int,
    val errorMessage: String? = null,
) {
    companion object {
        const val STATE_LOADING = 1
        const val STATE_SUCCESS = 2
        const val STATE_ERROR = 3
        const val STATE_ERROR_NO_BLOCKING = 4

        fun errorNoBlocking() = LoaderState(STATE_ERROR_NO_BLOCKING)
    }
}