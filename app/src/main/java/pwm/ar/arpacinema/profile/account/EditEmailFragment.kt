package pwm.ar.arpacinema.profile.account

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
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

        emailLayout.editText?.addTextChangedListener(TextValidator(emailLayout, TextValidator.Companion::isValidEmail))
        setEmailButton.setOnClickListener {
            if (emailLayout.error != null || emailLayout.editText?.text.isNullOrBlank()) {
                Snackbar.make(view, "E-mail non valida", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                try {
                    viewModel.updateEmail()
                    Snackbar.make(view, "E-mail aggiornata con successo", Snackbar.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Snackbar.make(view, "Errore nell'aggiornamento della e-mail", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
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
