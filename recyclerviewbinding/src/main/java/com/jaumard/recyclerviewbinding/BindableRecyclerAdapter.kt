package com.jaumard.recyclerviewbinding

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ObservableList.OnListChangedCallback
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.lang.ref.WeakReference

open class BindableRecyclerAdapter<T>(items: List<T>?, private var itemBinder: MultipleTypeItemBinder) : RecyclerView.Adapter<BindableRecyclerAdapter.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    private var onClickListener: OnClickListener<T>? = null
    private val callback = WeakReferenceOnListChangedCallback(this)
    val items = ObservableArrayList<T>()

    init {
        if (items != null) {
            this.items.addAll(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater!!, itemBinder.getItemLayout(viewType), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return itemBinder.getItemType((items[position] as T)!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.setVariable(itemBinder.variableId, items[position])
        for (data in itemBinder.additionalVariables) {
            binding.setVariable(data.first!!, data.second)
        }
        binding.root.tag = items[position]
        if (onClickListener != null) {
            binding.root.setOnClickListener { view -> onClickListener!!.onClick(view.tag as T) }
        }
        binding.executePendingBindings()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.items.addOnListChangedCallback(callback)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.items.removeOnListChangedCallback(callback)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<T>) {
        this.items.removeOnListChangedCallback(callback)
        this.items.clear()
        this.items.addAll(items)
        this.items.addOnListChangedCallback(callback)
        notifyDataSetChanged()
    }

    fun setItemBinder(itemBinder: MultipleTypeItemBinder) {
        this.itemBinder = itemBinder
    }

    class ViewHolder internal constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnClickListener(onClickListener: OnClickListener<T>?) {
        this.onClickListener = onClickListener
    }

    private class WeakReferenceOnListChangedCallback<T>(bindingRecyclerViewAdapter: BindableRecyclerAdapter<T>) : OnListChangedCallback<ObservableList<T>>() {

        private val adapterReference: WeakReference<BindableRecyclerAdapter<*>> = WeakReference(bindingRecyclerViewAdapter)

        override fun onChanged(sender: ObservableList<T>) {
            val adapter = adapterReference.get()
            adapter?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    interface OnClickListener<in T> {
        fun onClick(item: T)
    }
}
