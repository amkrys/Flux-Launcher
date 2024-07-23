package com.flux.launcher.presentation.activity

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.flux.launcher.R
import com.flux.launcher.base.BaseActivity
import com.flux.launcher.base.BaseTabAdapter
import com.flux.launcher.databinding.ActivityLauncherBinding
import com.flux.launcher.domain.model.FilterUiModel
import com.flux.launcher.presentation.fragment.apps.adapter.AppsFilterAdapter
import com.flux.launcher.presentation.fragment.apps.view.AppsFragment
import com.flux.launcher.util.extension.interceptScrollWhenIdle
import com.flux.launcher.util.extension.openStatusBar
import com.flux.launcher.util.extension.requireContext
import com.flux.launcher.util.helper.OnSwipeTouchListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.CornerMaterialSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : BaseActivity() {

    private var _binding: ActivityLauncherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LauncherViewModel by viewModels()

    private lateinit var bottomSheetBehavior: CornerMaterialSheetBehavior<ConstraintLayout>

    private lateinit var gestureDetector: GestureDetector

    private lateinit var systemInsets: Insets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observerInsetsListener()
        initGestureDetector()
        initBottomSheet()
        initTabLayout()
        initFilters()
    }

    private fun observerInsetsListener() = with(binding.bottomSheet.bottomSheetContainer) {
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }
    }

    private fun initGestureDetector() {
        gestureDetector = GestureDetector(requireContext(), object : OnSwipeTouchListener() {
            override fun onSwipeUp() {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSwipeDown() {
                openStatusBar()
            }
        })
    }

    private fun initBottomSheet() = with(binding.bottomSheet) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer) as CornerMaterialSheetBehavior
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomSheetContainer.apply {
                    setPadding(
                        paddingLeft,
                        (systemInsets.top * slideOffset).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }
        })
    }

    private fun initTabLayout() = with(binding.bottomSheet) {
        val adapter = BaseTabAdapter(supportFragmentManager, lifecycle).apply {
            addFrag(AppsFragment.newInstance())
            addFrag(AppsFragment.newInstance())
        }
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        viewPager.interceptScrollWhenIdle()
        addTabLayoutMediator()
    }

    private fun addTabLayoutMediator() = with(binding.bottomSheet) {
        val listOfTitles = arrayListOf(
            getString(R.string.apps), getString(R.string.widgets)
        )
        val listOfIcons = arrayListOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_apps),
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_widgets)
        )
        TabLayoutMediator(
            tabs, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = listOfTitles[position]
            tab.icon = listOfIcons[position]
        }.attach()
    }

    private fun initFilters() = with(binding.bottomSheet.rvFilters) {
        adapter = AppsFilterAdapter(arrayListOf<FilterUiModel>().apply {
            add(
                FilterUiModel(
                    icon = ContextCompat.getDrawable(this@LauncherActivity, R.drawable.ic_all_apps),
                    isSelected = true,
                    label = "All"
                )
            )
            add(
                FilterUiModel(
                    icon = ContextCompat.getDrawable(
                        this@LauncherActivity,
                        R.drawable.ic_recent_apps
                    ), label = "Recent"
                )
            )
            add(
                FilterUiModel(
                    icon = ContextCompat.getDrawable(this@LauncherActivity, R.drawable.ic_fav_apps),
                    label = "Favorites"
                )
            )
            add(
                FilterUiModel(
                    icon = ContextCompat.getDrawable(
                        this@LauncherActivity,
                        R.drawable.ic_colored_apps
                    ), label = "Coloured"
                )
            )
        }) {
            val adapter = adapter as AppsFilterAdapter
            adapter.changeSelection(adapter.getPosition(it))
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}