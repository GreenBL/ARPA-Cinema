package pwm.ar.arpacinema.profile.account

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.FragmentSecurityBinding
import pwm.ar.arpacinema.model.Questions
import pwm.ar.arpacinema.repository.DTO

class SecurityFragment : Fragment() {

    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SecurityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.navappbar.viewTitle.text = "Sicurezza"

        val dropdownMenu = binding.securityQuestionSpinner
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_dropdown_item,
            Questions.values().map { it.question }
        )
        dropdownMenu.setAdapter(arrayAdapter)

        binding.answer.editText?.doAfterTextChanged {
            updateSaveButtonState()
        }

        binding.button5.setOnClickListener {
            saveSecurityQuestionAndAnswer()
        }
    }

    private fun observeViewModel() {
        viewModel.securityQuestionResult.observe(viewLifecycleOwner) { response ->
            if (response.status == DTO.Stat.SUCCESS) {
                Toast.makeText(context, "Domanda di sicurezza salvata con successo", Toast.LENGTH_SHORT).show()
                // You might want to navigate back or clear the fields here
            } else if (response.status == DTO.Stat.ERROR) {
                Toast.makeText(context, "Errore: ${response.error}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Risposta non riconosciuta", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun updateSaveButtonState() {
        binding.button5.isEnabled = binding.securityQuestionSpinner.text.isNotEmpty() &&
                !binding.answer.editText?.text.isNullOrEmpty()
    }

    private fun saveSecurityQuestionAndAnswer() {
        val selectedQuestion = binding.securityQuestionSpinner.text.toString()
        val answer = binding.answer.editText?.text.toString()

        val questionId = Questions.values().find { it.question == selectedQuestion }?.questionId
            ?: return

        val userId = Session.user?.id?.toString()?.toInt() ?: return
        viewModel.addSecurityQuestionAndAnswer(userId, questionId, answer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}