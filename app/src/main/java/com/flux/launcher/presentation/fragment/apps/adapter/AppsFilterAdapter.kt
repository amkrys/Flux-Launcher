package com.flux.launcher.presentation.fragment.apps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flux.launcher.databinding.ItemFilterBinding
import com.flux.launcher.domain.model.FilterUiModel
import com.flux.launcher.widget.edgefactory.BaseViewHolder

class AppsFilterAdapter(
    private val list: MutableList<FilterUiModel>,
    private val onItemClick: (FilterUiModel) -> Unit
): RecyclerView.Adapter<AppsFilterAdapter.FilterViewHolder>() {

    private var lastSelectedIndex = 0

    class FilterViewHolder(binding: ItemFilterBinding) : BaseViewHolder(binding.root) {
        var binding: ItemFilterBinding
            internal set

        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FilterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.binding.position = position
        holder.binding.item = getItemData(position)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(getItemData(position))
        }
    }

    fun getCurrentList() = list

    fun changeSelection(position: Int, isMultiSelect: Boolean = false) {
        if (lastSelectedIndex == position) {
            return
        }
        for (i in list.indices) {
            if (position == i) {
                lastSelectedIndex = position
                list[i].isSelected = !list[i].isSelected
            } else {
                if (!isMultiSelect) {
                    list[i].isSelected = false
                    notifyItemChanged(i, list[i])
                }
            }
        }
        notifyItemChanged(position, list[position])
    }

    fun getItemData(position: Int) = list[position]

    fun getPosition(item: FilterUiModel) = list.indexOf(item)

}