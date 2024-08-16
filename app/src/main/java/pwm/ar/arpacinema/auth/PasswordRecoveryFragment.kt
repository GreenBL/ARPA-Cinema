package pwm.ar.arpacinema.auth

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment() {

    companion object {
        fun newInstance() = PasswordRecoveryFragment()
    }

    private var _binding : FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordRecoveryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstStepGroup = binding.stepOne
        val secondStepGroup = binding.stepTwo
        val sendButton = binding.stepOne.sendEmail

        secondStepGroup.root.alpha = 0f

        sendButton.setOnClickListener {
            // fade out first step
            firstStepGroup.root.animate().alpha(0f).setDuration(500).start()
            firstStepGroup.root.visibility = View.GONE

            // todo RECOVERY LOGIC!!!

            secondStepGroup.root.visibility = View.VISIBLE
            // fade in second step
            secondStepGroup.root.animate().alpha(1f).setDuration(500).start()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}