package com.jaumard.recyclerviewbinding

import android.support.annotation.LayoutRes
import android.support.v4.util.Pair
import java.util.*

abstract class MultipleTypeItemBinder(val variableId: Int,
                                      @LayoutRes @get:LayoutRes val layoutId: Int = 0,
                                      val additionalVariables: List<Pair<Int, Any>> = ArrayList()) {
    companion object {
        @JvmStatic
        protected val TYPE_NONE = 0
    }

    constructor(variableId: Int,
                additionalVariables: List<Pair<Int, Any>> = ArrayList()) : this(variableId, 0, additionalVariables)

    abstract fun getItemType(item: Any): Int

    @LayoutRes
    abstract fun getItemLayout(itemType: Int): Int
}
