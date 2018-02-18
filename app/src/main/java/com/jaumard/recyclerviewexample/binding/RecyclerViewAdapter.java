package com.jaumard.recyclerviewexample.binding;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;

import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter;
import com.jaumard.recyclerviewbinding.ItemBinder;
import com.jaumard.recyclerviewbinding.MultipleTypeItemBinder;
import com.jaumard.recyclerviewbinding.RecyclerViewBindingAdapter;
import com.jaumard.recyclerviewexample.BR;
import com.jaumard.recyclerviewexample.R;
import com.jaumard.recyclerviewexample.data.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter {
    @BindingAdapter({"items", "itemLayout"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout) {
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), null);
    }

    @BindingAdapter({"items", "itemLayout", "clickHandler"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout,
                                            BindableRecyclerAdapter.OnClickListener<T> listener) {
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), listener);
    }

    @BindingAdapter({"items", "itemLayout", "additionalData"})
    public static void setRecyclerData(RecyclerView recyclerView, List<RecyclerItem> items, @LayoutRes int layoutData, String addData) {
        List<Pair<Integer, Object>> additionalData = new ArrayList<>();
        additionalData.add(new Pair<Integer, Object>(BR.additionalData, addData));
        ItemBinder itemBinder = new ItemBinder(BR.data, layoutData, additionalData);
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, itemBinder, null);
    }

    @BindingAdapter({"items", "additionalData", "clickHandler"})
    public static void setRecyclerData(RecyclerView recyclerView, List<RecyclerItem> items, String addData, BindableRecyclerAdapter.OnClickListener clickHandler) {
        List<Pair<Integer, Object>> additionalData = new ArrayList<>();
        additionalData.add(new Pair<Integer, Object>(BR.additionalData, addData));

        MultipleTypeItemBinder itemBinder = new MultipleTypeItemBinder(BR.data, additionalData) {
            @Override
            public int getItemLayout(int itemType) {
                return itemType == 0 ? R.layout.recycler_cat_item : R.layout.recycler_view_complex_item;
            }

            @Override
            public int getItemType(Object item) {
                RecyclerItem it = (RecyclerItem) item;
                return it.getName().contains("0") ? 0 : 1;
            }
        };
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, itemBinder, clickHandler);
    }
}
