package com.aids61517.fragmentresultdemo.delegation.series2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.aids61517.fragmentresultdemo.delegation.series2.launcher.DialogFragmentLauncher
import com.aids61517.fragmentresultdemo.delegation.series2.launcher.FragmentLauncher
import java.util.concurrent.atomic.AtomicInteger

class FragmentResultManager : FragmentResult {
    private val nextFragmentTagCode = AtomicInteger()
    private val registerInteractionMap = mutableMapOf<String, Any?>()

    override fun <Interaction> registerDialogFragment(
        activity: FragmentActivity,
        interaction: (() -> Interaction)?,
    ): DialogFragmentLauncher {
        checkLifecycleState(activity)
        return registerForDialogAction(activity.javaClass.simpleName, interaction) {
            activity.supportFragmentManager
        }
    }

    override fun <Interaction> registerDialogFragment(
        fragment: Fragment,
        interaction: (() -> Interaction)?,
    ): DialogFragmentLauncher {
        checkLifecycleState(fragment)
        return registerForDialogAction(fragment.javaClass.simpleName, interaction) {
            fragment.childFragmentManager
        }
    }

    private fun <T> registerForDialogAction(
        registerClassName: String,
        interaction: (() -> T)?,
        getFragmentManager: () -> FragmentManager,
    ): DialogFragmentLauncher {
        val fragmentTag = createFragmentTag(registerClassName)
        return registerForDialogActionLauncher(
            fragmentTag = fragmentTag,
            interaction = interaction,
            getFragmentManager = getFragmentManager,
        )
    }

    private fun <T> registerForDialogActionLauncher(
        fragmentTag: String,
        interaction: (() -> T)?,
        getFragmentManager: () -> FragmentManager,
    ): DialogFragmentLauncher {
        registerInteractionMap[fragmentTag] = interaction?.invoke()
        return DialogFragmentLauncher(fragmentTag, getFragmentManager)
    }

    override fun registerFragment(activity: FragmentActivity): FragmentLauncher {
        return registerFragment<Unit>(activity, null)
    }

    override fun registerFragment(fragment: Fragment): FragmentLauncher {
        return registerFragment<Unit>(fragment, null)
    }

    override fun <Interaction> registerFragment(
        activity: FragmentActivity,
        interaction: (() -> Interaction)?,
    ): FragmentLauncher {
        checkLifecycleState(activity)
        return registerFragment(activity.javaClass.simpleName, interaction) {
            activity.supportFragmentManager
        }
    }

    override fun <Interaction> registerFragment(
        fragment: Fragment,
        interaction: (() -> Interaction)?,
    ): FragmentLauncher {
        checkLifecycleState(fragment)
        return registerFragment(fragment.javaClass.simpleName, interaction) {
            fragment.childFragmentManager
        }
    }

    private fun <Interaction> registerFragment(
        registerClassName: String,
        interaction: (() -> Interaction)?,
        getFragmentManager: () -> FragmentManager,
    ): FragmentLauncher {
        val fragmentTag = createFragmentTag(registerClassName)
        return registerFragmentLauncher(
            fragmentTag = fragmentTag,
            interaction = interaction,
            getFragmentManager = getFragmentManager,
        )
    }

    private fun <Interaction> registerFragmentLauncher(
        fragmentTag: String,
        interaction: (() -> Interaction)?,
        getFragmentManager: () -> FragmentManager,
    ): FragmentLauncher {
        registerInteractionMap[fragmentTag] = interaction?.invoke()
        return FragmentLauncher(
            fragmentTag,
            getFragmentManager,
        )
    }

    override fun findRegisterInteraction(fragmentTag: String): Any? {
        return registerInteractionMap[fragmentTag]
    }

    private fun createFragmentTag(registerClassName: String): String {
        return "FragmentResult_${registerClassName}_${nextFragmentTagCode.getAndIncrement()}"
    }

    private fun checkLifecycleState(activity: FragmentActivity) {
        val lifecycle = activity.lifecycle
        check(!lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            ("LifecycleOwner $activity is "
                + "attempting to register while current state is "
                + "${lifecycle.currentState}. LifecycleOwners must call register before "
                + "they are STARTED.")
        }
    }

    private fun checkLifecycleState(fragment: Fragment) {
        val lifecycle = fragment.lifecycle
        check(!lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            ("Fragment $this is attempting to "
                + "registerForAlertDialogAction after being created. Fragments must call "
                + "registerForAlertDialogAction() before they are created (i.e. initialization, "
                + "onAttach(), or onCreate()).")
        }
    }
}