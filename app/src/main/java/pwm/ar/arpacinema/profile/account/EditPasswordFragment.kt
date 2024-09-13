package pwm.ar.arpacinema.profile.account

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentEditPasswordBinding
import pwm.ar.arpacinema.util.TextValidator

class EditPasswordFragment : Fragment() {

    private var _binding: FragmentEditPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupTopBar()

        val passwordField = binding.passwordLayout
        val confirmPasswordButton = binding.setPasswordButton

        passwordField.editText?.addTextChangedListener(TextValidator(passwordField, TextValidator.Companion::isValidPassword))

        confirmPasswordButton.setOnClickListener {
            val passwordText = passwordField.editText?.text.toString()
            if (passwordText.isBlank()) {
                Snackbar.make(view, "Password vuota", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordField.error != null) {
                Snackbar.make(view, "Password non valida", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val success = viewModel.updatePassword()
                if (success) {
                    Snackbar.make(view, "Password aggiornata con successo", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(view, "Errore nell'aggiornamento della password", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupTopBar() {
        binding.inclusion.viewTitle.text = "Modifica password"
        binding.inclusion.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
