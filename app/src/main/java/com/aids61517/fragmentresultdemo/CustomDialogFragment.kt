package com.aids61517.fragmentresultdemo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.aids61517.fragmentresultdemo.databinding.CustomDialogFragmentBinding
import com.aids61517.fragmentresultdemo.delegation.series2.getInteractionFromParent
import kotlin.properties.Delegates

class CustomDialogFragment : DialogFragment() {
    companion object {
        fun newInstance() = CustomDialogFragment().apply {
            arguments = bundleOf()
        }
    }

    interface Interaction {
        fun onConfirmButtonClicked()

        fun onCancelButtonClicked()
    }

    private lateinit var binding: CustomDialogFragmentBinding

    private val interaction by Delegates.getInteractionFromParent<Interaction>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            interaction.onConfirmButtonClicked()
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            interaction.onCancelButtonClicked()
            dismiss()
        }
    }
}