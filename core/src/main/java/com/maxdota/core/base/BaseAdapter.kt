package com.maxdota.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, Y : BaseAdapter.BaseHolder<T>> : RecyclerView.Adapter<Y>() {
  protected var dataList: MutableList<T> = mutableListOf()

  fun updateDataAt(index: Int, item: T) {
    dataList[index] = item
    notifyItemChanged(index)
  }

  fun setData(list: List<T>) {
    dataList.clear()
    dataList.addAll(list)
    notifyDataSetChanged()
  }

  fun clearData() {
    dataList.clear()
    notifyDataSetChanged()
  }

  abstract fun newHolder(view: View, viewType: Int): Y
  abstract fun layoutRes(viewType: Int): Int

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Y {
    val view = LayoutInflater.from(parent.context).inflate(layoutRes(viewType), parent, false)
    return newHolder(view, viewType)
  }

  override fun getItemCount(): Int = dataList.size

  override fun onBindViewHolder(holder: Y, position: Int) {
    holder.setBaseHolderData(position, dataList[position])
  }

  abstract class BaseHolder<T>(protected val view: View) : RecyclerView.ViewHolder(view) {
    var pos: Int = -1
    var item: T? = null

    fun setBaseHolderData(position: Int, item: T?) {
      pos = position
      this.item = item
      val item = item ?: return
      setData(position, item)
    }

    abstract fun setData(position: Int, item: T)
  }
}