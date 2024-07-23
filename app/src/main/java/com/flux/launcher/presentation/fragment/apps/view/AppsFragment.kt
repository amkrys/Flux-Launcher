package com.flux.launcher.presentation.fragment.apps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.flux.launcher.databinding.FragmentAppsBinding
import com.flux.launcher.presentation.activity.LauncherViewModel
import com.flux.launcher.presentation.fragment.apps.adapter.AppsAdapter
import com.flux.launcher.util.constant.Constants
import com.flux.launcher.util.extension.assignItemDecoration
import com.flux.launcher.widget.edgefactory.BaseEdgeEffectFactory
import com.flux.launcher.widget.itemdecoration.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AppsFragment : Fragment() {

    private var _binding: FragmentAppsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LauncherViewModel by activityViewModels()

    private val appsAdapter: AppsAdapter by lazy {
        AppsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initObservers()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        assignItemDecoration(
            SpaceItemDecoration(
                verticalSpace = Constants.GRID_VERTICAL_SPACING
            )
        )
        layoutManager = GridLayoutManager(requireContext(), Constants.APP_LIST_SPAN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (appsAdapter.getItemViewType(position)) {
                        AppsAdapter.ViewType.HEADER.ordinal -> {
                            Constants.APP_LIST_SPAN_COUNT
                        }

                        else -> {
                            1
                        }
                    }
                }
            }
        }
        edgeEffectFactory = BaseEdgeEffectFactory()
        adapter = appsAdapter
        setHasFixedSize(true)
    }

    private fun initObservers() = lifecycleScope.launch {
        viewModel.appListLiveData.observe(viewLifecycleOwner) {
            appsAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = AppsFragment()
    }

}