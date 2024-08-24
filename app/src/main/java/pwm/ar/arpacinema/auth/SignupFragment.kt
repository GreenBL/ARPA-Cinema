package pwm.ar.arpacinema.auth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentSignupBinding
import pwm.ar.arpacinema.util.TextValidator

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameField: TextInputLayout
    private lateinit var surnameField: TextInputLayout
    private lateinit var phoneField: TextInputLayout
    private lateinit var emailField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var signUpButton: ExtendedFloatingActionButton

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Animation container transform
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        }

        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 200L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val closeButton = binding.topBarInclude.closeButton
        nameField = binding.nameFieldLayout
        surnameField = binding.surnameFieldLayout
        phoneField = binding.phoneFieldLayout
        emailField = binding.emailFieldLayout
        passwordField = binding.passwordFieldLayout
        signUpButton = binding.floatingActionButton

        setupErrorHighlight()
        setupNavigation(closeButton)
        setupSignUpAction()
        binding.topBarInclude.label.text = "Inserisci i tuoi dati"
    }

    private fun setupErrorHighlight() {
        nameField.editText!!.addTextChangedListener(
            TextValidator(nameField, TextValidator.Companion::isValidName)
        )
        surnameField.editText!!.addTextChangedListener(
            TextValidator(surnameField, TextValidator.Companion::isValidName)
        )
        phoneField.editText!!.addTextChangedListener(
            TextValidator(phoneField, TextValidator.Companion::isValidPhone)
        )
        emailField.editText!!.addTextChangedListener(
            TextValidator(emailField, TextValidator.Companion::isValidEmail)
        )
        passwordField.editText!!.addTextChangedListener(
            TextValidator(passwordField, TextValidator.Companion::isValidPassword)
        )
    }

    private fun setupNavigation(closeButton: MaterialButton) {
        closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupSignUpAction() {
        signUpButton.setOnClickListener {
            disableFields()

            lifecycleScope.launch {
                try {
                    delay(1000L)
                    val result = viewModel.signUp()

                    if (result?.error == null && result?.message == "SUCCESS") {
                        Dialog.showSignupSuccessDialog(requireContext())
                        findNavController().popBackStack()
                    } else {
                        Snackbar.make(requireView(), result?.error ?: "Unknown error", Snackbar.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Snackbar.make(requireView(), "Network error: ${e.localizedMessage}", Snackbar.LENGTH_SHORT).show()
                } finally {
                    enableFields()
                }
            }
        }
    }

    private fun disableFields() {
        nameField.editText!!.isEnabled = false
        surnameField.editText!!.isEnabled = false
        phoneField.editText!!.isEnabled = false
        emailField.editText!!.isEnabled = false
        passwordField.editText!!.isEnabled = false
        signUpButton.isEnabled = false
    }

    private fun enableFields() {
        nameField.editText!!.isEnabled = true
        surnameField.editText!!.isEnabled = true
        phoneField.editText!!.isEnabled = true
        emailField.editText!!.isEnabled = true
        passwordField.editText!!.isEnabled = true
        signUpButton.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
