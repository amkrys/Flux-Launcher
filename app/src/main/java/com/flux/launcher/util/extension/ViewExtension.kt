package com.flux.launcher.util.extension

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
    action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}

fun RecyclerView.assignItemDecoration(decoration: RecyclerView.ItemDecoration) {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
    addItemDecoration(decoration)
}

fun ViewPager2.interceptScrollWhenIdle() {
    children.find { it is RecyclerView }?.let {
        (it as RecyclerView).isNestedScrollingEnabled = false
    }
}