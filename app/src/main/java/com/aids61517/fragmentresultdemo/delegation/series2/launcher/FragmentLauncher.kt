package com.aids61517.fragmentresultdemo.delegation.series2.launcher

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class FragmentLauncher(
    val fragmentTag: String,
    private val getFragmentManager: () -> FragmentManager,
) {
    fun launch(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        addToBackStack: Boolean = false,
    ) {
        getFragmentManager().commit {
            replace(containerViewId, fragment, fragmentTag)
            if (addToBackStack) {
                addToBackStack(fragmentTag)
            }
        }
    }
}