package pwm.ar.arpacinema.auth

import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView.Validator
import android.widget.DatePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentSignupBinding
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.util.TextValidator
import java.text.SimpleDateFormat
import java.util.Locale

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameField: TextInputLayout
    private lateinit var surnameField: TextInputLayout
    private lateinit var phoneField: TextInputLayout
    private lateinit var emailField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var signUpButton: ExtendedFloatingActionButton

    companion object {
        fun newInstance() = SignupFragment()
    }

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // animation container transform
        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        }

        this.sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 200L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        }


        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // observe
        viewModel.stat.observe(viewLifecycleOwner) { status ->
            when (status) {
                DTO.Stat.DEFAULT -> {
                    // nothing
                }
                DTO.Stat.SUCCESS -> {
                    Dialog.showSignupSuccessDialog(requireContext())
                    findNavController().popBackStack()
                }
                DTO.Stat.UNFILLED -> {
                    Dialog.showErrorDialog(requireContext()) // UNFILLED DIALOG TODO
                }
                DTO.Stat.ERROR -> {
                    Dialog.showNetworkErrorDialog(requireContext())
                }
                else -> {
                    Dialog.showErrorDialog(requireContext()) // GENERIC UNKNOWN ERROR TODO
                }
            }
        }

        val closeButton = binding.topBarInclude.closeButton
        nameField = binding.nameFieldLayout
        surnameField = binding.surnameFieldLayout
        phoneField = binding.phoneFieldLayout
        emailField = binding.emailFieldLayout
        passwordField = binding.passwordFieldLayout
        signUpButton = binding.floatingActionButton

        errorHighlight(nameField, surnameField, phoneField, emailField, passwordField)
        // handle closing of this
        setupNavBack(binding.floatingActionButton2)
        signUpAction(signUpButton)
        // temporarily disable the text fields


        binding.topBarInclude.label.text = "Inserisci i tuoi dati"


    }

    private fun signUpAction(signUpButton: ExtendedFloatingActionButton) {
        signUpButton.setOnClickListener {
            disableFields()
            lifecycleScope.launch {
                delay(1000L)
                viewModel.signUp()
                enableFields()
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

    private fun setupNavBack(closeButton: View) {
        closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun errorHighlight(
        nameField: TextInputLayout,
        surnameField: TextInputLayout,
        phoneField: TextInputLayout,
        emailField: TextInputLayout,
        passwordField: TextInputLayout
    ) {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//
//    private fun dateSelectionPopup() {
//
//        val today = MaterialDatePicker.todayInUtcMilliseconds()
//        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//
//        calendar.timeInMillis = today
//        calendar[Calendar.MONTH] = Calendar.JANUARY
//        val janThisYear = calendar.timeInMillis
//
//        calendar.timeInMillis = today
//        calendar[Calendar.MONTH] = Calendar.DECEMBER
//        val decThisYear = calendar.timeInMillis
//
//        val constraintsBuilder =
//            CalendarConstraints.Builder()
//                .setStart(janThisYear)
//                .setEnd(decThisYear)
//
//        val dateBox = MaterialDatePicker.Builder.datePicker()
//            .setTitleText("Seleziona la tua data di nascita")
//            .setSelection(Calendar.getInstance().timeInMillis)
//            .build()
//
//
//
//        dateBox.addOnPositiveButtonClickListener { selection ->
//
//            val selectionCalendar = Calendar.getInstance().apply {
//                timeInMillis = selection
//            }
//            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            val formattedDate = sdf.format(calendar.time)
//
//            binding.dateField.setText(formattedDate)
//        }
//
//        dateBox.show(parentFragmentManager, "DATE_PICKER")
//
//    }

}