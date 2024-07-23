package com.flux.launcher.presentation.activity

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.flux.launcher.R
import com.flux.launcher.base.BaseActivity
import com.flux.launcher.base.BaseTabAdapter
import com.flux.launcher.databinding.ActivityLauncherBinding
import com.flux.launcher.util.extension.openStatusBar
import com.flux.launcher.util.extension.requireContext
import com.flux.launcher.util.helper.OnSwipeTouchListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.CornerMaterialSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LauncherActivity : BaseActivity() {

    private var _binding: ActivityLauncherBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: CornerMaterialSheetBehavior<ConstraintLayout>

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initGestureDetector()
        initBottomSheet()
        initTabLayout()
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
                tabs.alpha = slideOffset
                tabs.isVisible = true
                text.alpha = 1 - (slideOffset * 10)
            }
        })
    }

    private fun initTabLayout() = with(binding.bottomSheet) {
        val adapter = BaseTabAdapter(supportFragmentManager, lifecycle).apply {
            addFrag(Fragment())
            addFrag(Fragment())
        }
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        addTabLayoutMediator()
    }

    private fun addTabLayoutMediator() = with(binding.bottomSheet) {
        val listOfTitles = arrayListOf(
            getString(R.string.apps), getString(R.string.widgets)
        )
        TabLayoutMediator(
            tabs, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = listOfTitles[position]
        }.attach()
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