package pwm.ar.arpacinema.profile.info

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pwm.ar.arpacinema.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.init()

        val back = binding.appbar.navBack
        binding.appbar.viewTitle.text = "Profilo"
        val saveButton = binding.saveButton
        val nameLayout = binding.nameLayout
        val surnameLayout = binding.surnameLayout
        val phoneLayout = binding.phoneLayout

        var initialized = false

        view.post {
            initialized = true
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        // Ascolta i cambiamenti nei campi per mostrare il saveButton
        nameLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(saveButton)
                initialized = false
            }
        }

        surnameLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(saveButton)
                initialized = false
            }
        }

        phoneLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(saveButton)
                initialized = false
            }
        }

        saveButton.setOnClickListener {
            viewModel.updateUser {
                // Nascondi il saveButton e mostra il Toast dopo l'aggiornamento
                fadeOut(saveButton)
                Snackbar.make(
                    requireView(), // La view da cui il Snackbar deve essere ancorato
                    "Dati aggiornati con successo!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun fadeIn(saveButton: Button) {
        saveButton.post {
            saveButton.visibility = View.VISIBLE
            saveButton.alpha = 0f
            saveButton.animate().alpha(1f).setDuration(350).start()
        }
    }

    private fun fadeOut(saveButton: Button) {
        saveButton.post {
            saveButton.animate().alpha(0f).setDuration(350).withEndAction {
                saveButton.visibility = View.GONE
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
