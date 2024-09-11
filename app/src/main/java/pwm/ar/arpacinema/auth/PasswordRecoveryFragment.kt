package pwm.ar.arpacinema.auth

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentPasswordRecoveryBinding
import pwm.ar.arpacinema.repository.DTO
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
        binding.stepOne.lifecycleOwner = viewLifecycleOwner
        binding.stepTwo.viewModel = viewModel
        binding.stepTwo.lifecycleOwner = viewLifecycleOwner
        binding.lifecycleOwner = viewLifecycleOwner

        val firstStepGroup = binding.stepOne
        val secondStepGroup = binding.stepTwo
        val resetPasswordButton = binding.stepTwo.confirmBtn
        val sendButton = binding.stepOne.sendEmail
        val progress = binding.progressBar2
        val questionText = binding.stepTwo.domandaText
        val answerField = binding.stepTwo.answerLayout


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
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it == DTO.Stat.DEFAULT) return@observe
            when (it) {
                DTO.Stat.SUCCESS -> {
                    firstStepGroup.root.animate().alpha(0f).setDuration(500).start()
                    firstStepGroup.root.visibility = View.INVISIBLE
                    secondStepGroup.root.visibility = View.VISIBLE
                    secondStepGroup.root.animate().alpha(1f).setDuration(500).start()
                }
                DTO.Stat.USER_NOT_REGISTERED -> Dialog.showUserNotFound(requireContext())
                DTO.Stat.PSW_ERROR -> {
                    answerField.error = "La risposta inserita non è corretta!"
                }
                DTO.Stat.NETWORK_ERROR -> Dialog.showNetworkErrorDialog(requireContext())
                DTO.Stat.ERROR -> Dialog.showImpossibleRecoveryDialog(requireContext())
                DTO.Stat.PASSWORD_EDITED -> {
                    Dialog.showEditedPasswordSuccessDialog(requireContext())
                    lifecycleScope.launch {
                        delay(500) // delay so the user doesnt see two things happening
                                            // at the same time
                        findNavController().popBackStack()
                    }

                }

                else -> Dialog.showErrorDialog(requireContext())
            }
        }

        // SECOND ACT

        viewModel.answer.observe(viewLifecycleOwner) {
            answerField.isErrorEnabled = false
            resetPasswordButton.isEnabled = viewModel.checkFields()
        }

        viewModel.newPassword.observe(viewLifecycleOwner) {
            resetPasswordButton.isEnabled = viewModel.checkFields()
        }

        resetPasswordButton.setOnClickListener {
            lifecycleScope.launch {
                progress.visibility = View.VISIBLE
                resetPasswordButton.isEnabled = false
                viewModel.assertAnswer()
                // close keyboard
                closeIme()
                resetPasswordButton.isEnabled = true
                progress.visibility = View.GONE

            }
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

    private fun closeIme() {
        val windowInsetsController = view?.windowInsetsController
        windowInsetsController?.hide(WindowInsets.Type.ime())
    }
}