package com.aids61517.fragmentresultdemo.delegation.series2

import androidx.fragment.app.Fragment
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T> Delegates.getInteractionFromParent() =
    object : ReadOnlyProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            thisRef.tag?.let { tag ->
                (thisRef.parentFragment as? FragmentResult
                    ?: thisRef.context as? FragmentResult)?.findRegisterInteraction(tag)
            }?.let { it as? T }
                ?.let { return it }

            ((thisRef.parentFragment as? T) ?: (thisRef.context as? T))?.let { return it }
            throw IllegalStateException("Can't retrieve callback. Parent must implement interaction.")
        }
    }

inline fun <reified T> Delegates.getInteractionFromParentOrNull() =
    object : ReadOnlyProperty<Fragment, T?> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
            thisRef.tag?.let { tag ->
                (thisRef.parentFragment as? FragmentResult
                    ?: thisRef.context as? FragmentResult)?.findRegisterInteraction(tag)
            }?.let { it as? T }
                ?.let { return it }

            return ((thisRef.parentFragment as? T) ?: (thisRef.context as? T))
        }
    }