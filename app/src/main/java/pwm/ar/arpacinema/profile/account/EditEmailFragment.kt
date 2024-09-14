package pwm.ar.arpacinema.profile.account

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentEditEmailBinding
import pwm.ar.arpacinema.util.TextValidator

class EditEmailFragment : Fragment() {

    private var _binding: FragmentEditEmailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditEmailFragment()
    }

    private val viewModel: EditEmailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditEmailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupTopBar()

        val setEmailButton = binding.setEmailButton
        val emailLayout = binding.emailLayout

        setEmailButton.isEnabled = false


        emailLayout.editText?.addTextChangedListener(TextValidator(emailLayout, TextValidator.Companion::isValidEmail))

        emailLayout.editText?.addTextChangedListener {
            val validatorFun = TextValidator.Companion::B_isValidEmail

            setEmailButton.isEnabled = validatorFun(it.toString())
        }


        setEmailButton.setOnClickListener {
            if (emailLayout.error != null || emailLayout.editText?.text.isNullOrBlank()) {
                Snackbar.make(view, "E-mail non valida", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            lifecycleScope.launch {
                try {
                    val success = viewModel.updateEmail()
                    if (success) {

                        emailLayout.isEnabled = false
                        setEmailButton.isEnabled = false

                        // Per la finestra di dialogo
                        showSuccessDialog()

                        findNavController().popBackStack()
                    } else {
                        Snackbar.make(view, "Errore nell'aggiornamento della e-mail", Snackbar.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Snackbar.make(view, "Errore nell'aggiornamento della e-mail", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Per mostrare la finestra di dialogo di Material 0.0.0-alpha12 ... ma anche no
    private fun showSuccessDialog() {
//        AlertDialog.Builder(requireContext())
//            .setTitle("Aggiornamento riuscito")
//            .setMessage("L'email è stata aggiornata con successo.")
//            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//            .show()
        Dialog.showEditEmailDialog(requireContext())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupTopBar() {
        binding.inclusion.viewTitle.text = "Modifica e-mail"
        binding.inclusion.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
