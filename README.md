# RecyclerViewBinding

Tired of creating recycler view adapter and recycler view holder on your Android applications ?

This library is for you ! Android data binding help you write less code, with this library it will be even less :)

It's easy to use, first install the library:

```groovy
implementation 'com.jaumard:recyclerviewbinding:1.0.0'
```

I assume that your project is already configured to use data binding, if not please follow [this steps](https://developer.android.com/topic/libraries/data-binding/index.html)

## Basic usage
For simple layout and items, just put this on your layout:

```xml
<android.support.v7.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="android.support.v7.widget.LinearLayoutManager"
            bind:itemLayout="@{@layout/recycler_view_item}"
            bind:items="@{viewModel.items}">

</android.support.v7.widget.RecyclerView>
```

What's new here ? You just specify the layout of your items under `itemLayout` and the list of your items in `items`.

This mean that your `recycler_view_item` can look like:

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <variable
            name="data"
            type="com.jaumard.recyclerviewexample.data.RecyclerItem"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?selectableItemBackground"
        android:clickable="true"
        android:focusable="true"
        android:orientation="vertical">

        <TextView
            style="?android:textAppearanceLarge"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{data.name}"/>

        <TextView
            style="?android:textAppearanceSmall"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{data.firstName}"/>
    </LinearLayout>
</layout>
```

As every data binding magic, you need a binding adapter to link those layout and the data, it as simple as:

```java
public class RecyclerViewAdapter {
    @BindingAdapter({"items", "itemLayout"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout) {
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), null);
    }
}
```

`RecyclerViewBindingAdapter` is provided by the library, all you need to do is passing the data.

## Click handling

I most cases you'll need to add a click event on your items, fear not because it's still easy to do :)

```xml
<android.support.v7.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="android.support.v7.widget.LinearLayoutManager"
            bind:clickHandler="@{clickHandler}"
            bind:itemLayout="@{@layout/recycler_view_item}"
            bind:items="@{viewModel.items}">

</android.support.v7.widget.RecyclerView>
```

The only difference is that you now pass a `clickHandler` to your recycler view, where `clickHandler` is:

```xml
<variable
    name="clickHandler"
    type="com.jaumard.recyclerviewbinding.BindableRecyclerAdapter.OnClickListener"/>
```

As we add a new data binding parameter, we need to also add it on our binding adapter like this:

```java
@BindingAdapter({"items", "itemLayout", "clickHandler"})
public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout,
                                        BindableRecyclerAdapter.OnClickListener<T> listener) {
    RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), listener);
}
```

## Complex usage

What about if I need more than one layout for my items ? Or need more data than just my item for data binding ?

It's all supported, isn't it great ?!

### Additional data
On our layout, still easy, just add the additional data you need:

```xml
<android.support.v7.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="android.support.v7.widget.LinearLayoutManager"
            bind:additionalData="@{viewModel.additionalData}"
            bind:itemLayout="@{@layout/recycler_view_complex_item}"
            bind:items="@{viewModel.items}">

</android.support.v7.widget.RecyclerView>
```

This additional data can be use under our item layout like this (additional data can be any type of data you need):

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="additionalData"
            type="String"/>

        <variable
            name="data"
            type="com.jaumard.recyclerviewexample.data.RecyclerItem"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?selectableItemBackground"
        android:clickable="true"
        android:orientation="vertical"
        android:focusable="true">

        <TextView
            style="?android:textAppearanceLarge"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{data.name}"/>

        <TextView
            style="?android:textAppearanceSmall"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{data.firstName}"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{additionalData}"/>
    </LinearLayout>
</layout>

```

Again we also need to edit our binding adapter to pass this additional data:

```java
@BindingAdapter({"items", "itemLayout", "additionalData"})
public static void setRecyclerData(RecyclerView recyclerView, List<RecyclerItem> items, @LayoutRes int layoutData, String addData) {
    List<Pair<Integer, Object>> additionalData = new ArrayList<>();
    additionalData.add(new Pair<Integer, Object>(BR.additionalData, addData));
    ItemBinder itemBinder = new ItemBinder(BR.data, layoutData, additionalData);
    RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, itemBinder, null);
}
```

### Multiple layouts

So now, if we want different layout depending of the type of our items, first step, remove the layout under our xml as it will be dynamic:

```xml
<android.support.v7.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="android.support.v7.widget.LinearLayoutManager"
            bind:additionalData="@{viewModel.additionalData}"
            bind:clickHandler="@{clickHandler}"
            bind:items="@{viewModel.items}">

</android.support.v7.widget.RecyclerView>
```

Then modify our binding adapter like this:

```java
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
```

So now out binding adapter is a little bit more complex but still ok, it use `MultipleTypeItemBinder`, you need to extend this class to tell the recycler view adapter which layout to use for a given item.

First `getItemType` is called with the current item to render, return here the type of your item (here 0 or 1, so 2 different items).

Then `getItemLayout` is called with the current item type to render (so 0 or 1 here), we just need to return now the layout to use for this type.

## Examples
This library come with a small example app, feel free to check it to see all the above cases.

## Licence

[MIT](https://github.com/jaumard/RecyclerViewBinding/blob/master/LICENSE)
