package com.aids61517.fragmentresultdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aids61517.fragmentresultdemo.delegation.series2.FragmentResult
import com.aids61517.fragmentresultdemo.delegation.series2.FragmentResultManager

class MainActivity : AppCompatActivity(),
    FragmentResult by FragmentResultManager() {

    private val customDialogLauncher = registerDialogFragment(this) {
        object : CustomDialogFragment.Interaction {
            override fun onConfirmButtonClicked() {
                Log.i("MainActivity", "onConfirmButtonClicked")
            }

            override fun onCancelButtonClicked() {
                Log.i("MainActivity", "onCancelButtonClicked")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            customDialogLauncher.launch(CustomDialogFragment.newInstance())
        }
    }
}