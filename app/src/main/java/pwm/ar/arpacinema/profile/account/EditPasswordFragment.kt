package pwm.ar.arpacinema.profile.account

import android.app.AlertDialog
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
import pwm.ar.arpacinema.common.Dialog
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
        binding.lifecycleOwner = viewLifecycleOwner
        setupTopBar()

        val passwordField = binding.passwordLayout
        val confirmPasswordButton = binding.setPasswordButton
        confirmPasswordButton.isEnabled = false

        viewModel.password.observe(viewLifecycleOwner) {
            val valid = TextValidator.B_isValidPassword(it)
            confirmPasswordButton.isEnabled = valid
        }


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
                try {
                    val success = viewModel.updatePassword()
                    if (success) {

                        passwordField.isEnabled = false
                        confirmPasswordButton.isEnabled = false

                        showSuccessDialog()

                        findNavController().popBackStack()
                    } else {
                        Snackbar.make(view, "Errore nell'aggiornamento della password", Snackbar.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Snackbar.make(view, "Errore nell'aggiornamento della password", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showSuccessDialog() {
//        AlertDialog.Builder(requireContext())
//            .setTitle("Aggiornamento riuscito")
//            .setMessage("La password è stata aggiornata con successo.")
//            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//            .show()
        Dialog.showEditedPasswordSuccessDialog(requireContext())
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
