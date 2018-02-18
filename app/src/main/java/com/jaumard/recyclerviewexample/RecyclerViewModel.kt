package com.jaumard.recyclerviewexample

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.jaumard.recyclerviewexample.data.RecyclerItem
import java.util.*

class RecyclerViewModel {
    companion object {
        private const val NB_ITEMS = 20
    }

    var items: ObservableField<List<RecyclerItem>> = ObservableField(ArrayList())
    var mode = ObservableInt(0)
    var additionalData = ObservableField("additionnal data")

    init {
        val items = (0 until NB_ITEMS).map { RecyclerItem("name " + it, "firstname " + it) }
        this.items.set(items)
    }

    fun setMode(mode: Int) {
        this.mode.set(mode)
    }
}
