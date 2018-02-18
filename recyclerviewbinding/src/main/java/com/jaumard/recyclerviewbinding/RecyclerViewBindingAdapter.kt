package com.jaumard.recyclerviewbinding

import android.support.v7.widget.RecyclerView

object RecyclerViewBindingAdapter {

    @JvmStatic
    fun <T> setRecyclerItems(recyclerView: RecyclerView,
                             items: List<T>,
                             itemBinder: MultipleTypeItemBinder,
                             listener: BindableRecyclerAdapter.OnClickListener<T>?) {
        var adapter: BindableRecyclerAdapter<T>? = recyclerView.adapter as? BindableRecyclerAdapter<T>

        if (adapter == null) {
            adapter = BindableRecyclerAdapter(items, itemBinder)
            adapter.setOnClickListener(listener)
            recyclerView.adapter = adapter
        } else {
            adapter.setOnClickListener(listener)
            adapter.setItemBinder(itemBinder)
            adapter.setItems(items)
        }
    }
}