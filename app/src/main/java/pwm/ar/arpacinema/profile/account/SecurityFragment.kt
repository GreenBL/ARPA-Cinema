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
import pwm.ar.arpacinema.common.Dialog
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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.navappbar.viewTitle.text = "Sicurezza"
        binding.navappbar.navBack.setOnClickListener{
            findNavController().popBackStack()
        }

        val button = binding.button5
        val dropdownMenu = binding.securityQuestionSpinner
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_dropdown_item,
            Questions.entries.map { it.question }
        )
        dropdownMenu.setAdapter(arrayAdapter)

        dropdownMenu.setOnItemClickListener { _, _, position, _ ->
            viewModel.questionId.postValue(position)
        }

        viewModel.questionId.observe(viewLifecycleOwner) {
            button.isEnabled = viewModel.assertFields()
        }

        viewModel.answer.observe(viewLifecycleOwner) {
            button.isEnabled = viewModel.assertFields()
        }

        button.setOnClickListener {
            viewModel.addSecurityQuestionAndAnswer()
        }
    }

    private fun observeViewModel() {
        viewModel.securityQuestionResult.observe(viewLifecycleOwner) { response ->
            // better approach
            val status = response.status
            when (status) {
                DTO.Stat.SUCCESS -> {
                    findNavController().popBackStack()
                    Dialog.show2FAuthenticationConfirmation(requireContext())
                }
                else -> {
                    Dialog.showErrorDialog(requireContext())
                }
            }
        }
    }


//    private fun updateSaveButtonState() {
//        binding.button5.isEnabled = binding.securityQuestionSpinner.text.isNotEmpty() &&
//                !binding.answer.editText?.text.isNullOrEmpty()
//    }

//    private fun saveSecurityQuestionAndAnswer() {
//        val selectedQuestion = binding.securityQuestionSpinner.text.toString()
//        val answer = binding.answer.editText?.text.toString()
//
//        val questionId = Questions.entries.find { it.question == selectedQuestion }?.questionId
//            ?: return
//
//        val userId = Session.user?.id?.toString()?.toInt() ?: return
//        viewModel.addSecurityQuestionAndAnswer(userId, questionId, answer)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}