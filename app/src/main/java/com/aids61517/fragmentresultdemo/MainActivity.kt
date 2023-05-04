package com.aids61517.fragmentresultdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(),
    CustomDialogFragment.Interaction {
    companion object {
        private const val FRAGMENT_TAG_CUSTOM_DIALOG = "FRAGMENT_TAG_CUSTOM_DIALOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val dialogFragment = CustomDialogFragment.newInstance()
            dialogFragment.show(supportFragmentManager, FRAGMENT_TAG_CUSTOM_DIALOG)
        }
    }

    override fun onConfirmButtonClicked() {
        Log.i("MainActivity", "onConfirmButtonClicked")
    }

    override fun onCancelButtonClicked() {
        Log.i("MainActivity", "onCancelButtonClicked")
    }
}