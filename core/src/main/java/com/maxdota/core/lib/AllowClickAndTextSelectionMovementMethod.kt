package com.maxdota.core.lib

import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

class AllowClickAndTextSelectionMovementMethod : LinkMovementMethod() {
  override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
    val action = event.action

    if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
      val x = event.x.toInt() - widget.totalPaddingLeft + widget.scrollX
      val y = event.y.toInt() - widget.totalPaddingTop + widget.scrollY

      val layout = widget.layout
      val line = layout.getLineForVertical(y)
      val off = layout.getOffsetForHorizontal(line, x.toFloat())

      val link = buffer.getSpans(off, off, ClickableSpan::class.java)

      if (link.isNotEmpty()) {
        if (action == MotionEvent.ACTION_UP) {
          link[0].onClick(widget)
        } else if (action == MotionEvent.ACTION_DOWN) {
          Selection.setSelection(
            buffer,
            buffer.getSpanStart(link[0]),
            buffer.getSpanEnd(link[0])
          )
        }
        return true
      } else {
        Selection.removeSelection(buffer)
      }
    }
    return super.onTouchEvent(widget, buffer, event)
  }
}