package pwm.ar.arpacinema.auth

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentPasswordRecoveryBinding
import pwm.ar.arpacinema.util.TextValidator

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
        navback()

        binding.viewModel = viewModel
        binding.stepOne.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val firstStepGroup = binding.stepOne
        val secondStepGroup = binding.stepTwo
        val sendButton = binding.stepOne.sendEmail
        val progress = binding.progressBar2
        val questionText = binding.stepTwo.domandaText


        secondStepGroup.root.alpha = 0f

        val passwordField = binding.stepTwo.newPasswordLayout
        passwordField.editText?.addTextChangedListener(TextValidator(passwordField, TextValidator.Companion::isValidPassword))

        viewModel.email.observe(viewLifecycleOwner) {
            sendButton.isEnabled = TextValidator.B_isValidEmail(it)
        }

        sendButton.setOnClickListener {

            lifecycleScope.launch {
                progress.visibility = View.VISIBLE
                sendButton.isEnabled = false
                viewModel.sendEmail()
                sendButton.isEnabled = true
                progress.visibility = View.GONE
            }





            // fade out first step
            firstStepGroup.root.animate().alpha(0f).setDuration(500).start()
            firstStepGroup.root.visibility = View.GONE

            // todo RECOVERY LOGIC!!!

            secondStepGroup.root.visibility = View.VISIBLE
            // fade in second step
            secondStepGroup.root.animate().alpha(1f).setDuration(500).start()
        }


    }

    private fun navback() {
        binding.include2.viewTitle.text = "Recupero password"
        binding.include2.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}