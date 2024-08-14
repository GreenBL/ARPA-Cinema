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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentSignupBinding
import pwm.ar.arpacinema.util.TextValidator
import java.text.SimpleDateFormat
import java.util.Locale

class SignupFragment : Fragment() {

    val sexOptions = listOf("Maschio", "Femmina", "LGBTQ+")

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SignupFragment()
    }

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // animation container transform
        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 1000L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            //scaleProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.5f, 1f)
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        }

        this.sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 1000L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            //scaleProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.5f, 1f)
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

        val closeButton = binding.topBarInclude.closeButton
        val nameField = binding.nameFieldLayout
        val surnameField = binding.surnameFieldLayout
        val phoneField = binding.phoneFieldLayout
        val emailField = binding.emailFieldLayout
        val passwordField = binding.passwordFieldLayout

        // TODO ERRORS ADDS MARGINS THAT DONT CLEAR WHEN NO ERROR IS PRESENT!!!!!

        emailField.editText!!.addTextChangedListener(
            TextValidator(emailField, TextValidator.Companion::isValidEmail)
        )
        passwordField.editText!!.addTextChangedListener(
            TextValidator(passwordField, TextValidator.Companion::isValidPassword)
        )
        nameField.editText!!.addTextChangedListener(
            TextValidator(nameField, TextValidator.Companion::isValidName)
        )
        surnameField.editText!!.addTextChangedListener(
            TextValidator(surnameField, TextValidator.Companion::isValidName)
        )
        phoneField.editText!!.addTextChangedListener(
            TextValidator(phoneField, TextValidator.Companion::isValidPhone)
        )



        // handle closing of this

        closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.topBarInclude.label.text = "Inserisci i tuoi dati"



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