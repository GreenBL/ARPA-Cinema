package pwm.ar.arpacinema.rewards.inventory

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentCodeBinding
import pwm.ar.arpacinema.databinding.FragmentFiltersBinding
import pwm.ar.arpacinema.discovery.FiltersFragment
import pwm.ar.arpacinema.discovery.FiltersViewModel

private val style = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered

class CodeFragment : DialogFragment() {

    private val binding: FragmentCodeBinding by lazy {
        FragmentCodeBinding.inflate(layoutInflater) // by lazy because we dont need to attach
    }

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private val viewModel: CodeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return MaterialAlertDialogBuilder(requireContext(), style)
            .setView(binding.root)
            .setTitle("get from args")
            .create()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = viewLifecycleOwner

    }
}