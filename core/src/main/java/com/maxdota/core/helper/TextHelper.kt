package com.maxdota.core.helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.maxdota.core.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

open class TextHelper {
  fun getTextBetween(originalText: String, startKey: String, endKey: String): String? {
    val startIndex = originalText.indexOf(startKey) + startKey.length
    val endIndex = originalText.indexOf(endKey, startIndex)
    if (startIndex < 0 || endIndex < 0 || endIndex < startIndex) {
      return null
    }
    return originalText.substring(startIndex, endIndex)
  }

  fun copyToClipboard(context: Context?, value: String?): Boolean {
    if (value.isNullOrBlank()) return false
    val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText(textHelperTag, value)
    clipboard?.setPrimaryClip(clip)
    return true
  }

  companion object {
    var textHelperTag = "TextHelper"
    private val moneyFormat = DecimalFormat("##,###,###")
    private val hourDateFormat = SimpleDateFormat("HH:mm dd/MM")
    private val dateMonthFormat = SimpleDateFormat("dd/MM")
    private val ccbootTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val fullTimeFormat = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")

    fun formatMoney(value: Double?) = moneyFormat.format(value ?: 0.0)
    fun formatMoney(value: Long?) = moneyFormat.format(value ?: 0L)
    fun formatMoney(value: Int?) = moneyFormat.format(value ?: 0)
    fun parseMoney(value: String) = moneyFormat.parse(value)?.toInt() ?: 0
    fun formatHourDate(value: Long) = hourDateFormat.format(Date(value))
    fun formatDateMonth(value: Long) = dateMonthFormat.format(Date(value))
    fun formatFullTime(value: Long) = fullTimeFormat.format(Date(value))

    fun base64WithoutPrefix(data: String) = if (data.isEmpty()) {
      ""
    } else data.substring(data.indexOf("base64,") + "base64,".length)

    fun loadBase64ToImageView(
      base64String: String?,
      imageView: ImageView,
      defaultImageRes: Int = R.drawable.baseline_image_24
    ): Boolean {
      if (base64String.isNullOrBlank()) {
        imageView.setImageResource(defaultImageRes)
        return false
      } else {
        try {
          val bytes = Base64.decode(base64String, Base64.DEFAULT)
          val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
          imageView.setImageBitmap(bitmap)
          return true
        } catch (ex: Exception) {
          L.e(textHelperTag, "loadBase64ToImageView failed\n${ex.message}\n$base64String")
          imageView.setImageResource(defaultImageRes)
          return false
        }
      }
    }
  }
}