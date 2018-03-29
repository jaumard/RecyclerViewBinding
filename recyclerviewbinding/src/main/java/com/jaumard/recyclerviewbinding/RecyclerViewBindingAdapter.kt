package com.jaumard.recyclerviewbinding

import android.support.v7.widget.RecyclerView

object RecyclerViewBindingAdapter {

    @JvmStatic
    fun <T> setRecyclerItems(recyclerView: RecyclerView,
                             items: List<T>,
                             itemBinder: MultipleTypeItemBinder,
                             listener: BindableRecyclerAdapter.OnClickListener<T>?) {
        setRecyclerItems(recyclerView, items, itemBinder, listener, BindableRecyclerAdapter(items, itemBinder))
    }

    @JvmStatic
    fun <T> setRecyclerItems(recyclerView: RecyclerView,
                             items: List<T>,
                             itemBinder: MultipleTypeItemBinder,
                             listener: BindableRecyclerAdapter.OnClickListener<T>?,
                             adapter: BindableRecyclerAdapter<T>) {
        val existingAdapter: BindableRecyclerAdapter<T>? = recyclerView.adapter as? BindableRecyclerAdapter<T>

        if (existingAdapter == null) {
            adapter.setOnClickListener(listener)
            recyclerView.adapter = adapter
        } else {
            existingAdapter.setOnClickListener(listener)
            existingAdapter.setItemBinder(itemBinder)
            existingAdapter.setItems(items)
        }
    }
}