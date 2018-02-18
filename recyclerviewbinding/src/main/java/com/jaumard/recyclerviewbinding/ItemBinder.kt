package com.jaumard.recyclerviewbinding

import android.support.annotation.LayoutRes
import android.support.v4.util.Pair

class ItemBinder(variable: Int,
                 layout: Int = 0,
                 addVariables: List<Pair<Int, Any>> = ArrayList()) : MultipleTypeItemBinder(variable, layout, addVariables) {

    constructor(@LayoutRes
                layoutId: Int, variableId: Int) : this(variableId, layoutId, ArrayList())

    override fun getItemType(item: Any): Int {
        return TYPE_NONE
    }

    @LayoutRes
    override fun getItemLayout(itemType: Int): Int {
        return layoutId
    }
}
