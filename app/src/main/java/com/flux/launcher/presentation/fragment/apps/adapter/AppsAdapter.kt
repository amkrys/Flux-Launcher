package com.flux.launcher.presentation.fragment.apps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flux.launcher.databinding.ItemAppGridBinding
import com.flux.launcher.databinding.ItemAppListBinding
import com.flux.launcher.databinding.ItemHeaderBinding
import com.flux.launcher.domain.model.AppUiModel
import com.flux.launcher.widget.edgefactory.BaseViewHolder

class AppsAdapter : ListAdapter<AppUiModel, RecyclerView.ViewHolder>(DiffCallback()) {

    private var isGrid = true

    class LetterViewHolder(binding: ItemHeaderBinding) : BaseViewHolder(binding.root) {
        var binding: ItemHeaderBinding
            internal set

        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    class GridAppViewHolder(binding: ItemAppGridBinding) : BaseViewHolder(binding.root) {
        var binding: ItemAppGridBinding
            internal set

        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    class ListAppViewHolder(binding: ItemAppListBinding) : BaseViewHolder(binding.root) {
        var binding: ItemAppListBinding
            internal set

        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> {
                val binding = ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LetterViewHolder(binding)
            }
            ViewType.GRID_ITEM.ordinal -> {
                val binding = ItemAppGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                GridAppViewHolder(binding)
            }
            else -> {
                val binding = ItemAppListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ListAppViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItemData(position).isHeader) {
            ViewType.HEADER.ordinal
        } else {
            if (isGrid) {
                ViewType.GRID_ITEM.ordinal
            } else {
                ViewType.LIST_ITEM.ordinal
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LetterViewHolder -> {
                holder.binding.position = position
                holder.binding.item = getItemData(position)
            }
            is GridAppViewHolder -> {
                holder.binding.item = getItemData(position)
            }
            is ListAppViewHolder -> {
                holder.binding.item = getItemData(position)
            }
        }
    }

    fun getItemData(position: Int): AppUiModel = currentList[position]

    class DiffCallback : DiffUtil.ItemCallback<AppUiModel>() {
        override fun areItemsTheSame(oldItem: AppUiModel, newItem: AppUiModel) =
            oldItem.packageName == newItem.packageName

        override fun areContentsTheSame(oldItem: AppUiModel, newItem: AppUiModel) =
            oldItem == newItem
    }

    enum class ViewType {
        HEADER, GRID_ITEM, LIST_ITEM
    }

}