package pwm.ar.arpacinema.profile.wallet

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.databinding.FragmentWalletBinding
import pwm.ar.arpacinema.repository.DTO

class WalletFragment : Fragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val confirmButton = binding.confirmButton
        val increaseLayout = binding.increaseLayout

        val title = binding.include.viewTitle
        title.text = "Portafoglio"

        val backButton = binding.include.navBack
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        var initialized = false

        view.post {
            initialized = true
        }

        // on text changed -> check if it's empty -> disable button if it is
//        increaseLayout.editText?.addTextChangedListener {
//            if (it.isNullOrBlank()) {
//                disable(confirmButton)
//            } else {
//                enable(confirmButton)
//            }
//        }

        viewModel.inputAmount.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                disable(confirmButton)
            } else {
                enable(confirmButton)
            }
        }

        confirmButton.setOnClickListener {
            disable(confirmButton)
            lifecycleScope.launch {
                viewModel.increaseBalance()
            }
            viewModel.inputAmount.postValue("")
        }

        viewModel.responseStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                DTO.Stat.DEFAULT -> {
                    // do nothing because we just opened the view mate
                }
                DTO.Stat.SUCCESS -> {
                    Snackbar.make(requireView(), "Saldo caricato", Snackbar.LENGTH_SHORT).show()
                    viewModel.inputAmount.postValue(null)
                } else -> {
                    Snackbar.make(requireView(), "Errore", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun enable(button: Button) {
        button.isEnabled = true
    }

    private fun disable(button: Button) {
        button.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
