package pwm.ar.arpacinema.profile.info

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
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
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.init()

        val back = binding.appbar.navBack
        binding.appbar.viewTitle.text = "Profilo"
        val editImageButton = binding.editProfileImg
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
//        nameLayout.editText?.addTextChangedListener {
//            if (initialized) {
//                fadeIn(saveButton)
//                initialized = false
//            }
//        }
//
//        surnameLayout.editText?.addTextChangedListener {
//            if (initialized) {
//                fadeIn(saveButton)
//                initialized = false
//            }
//        }
//
//        phoneLayout.editText?.addTextChangedListener {
//            if (initialized) {
//                fadeIn(saveButton)
//                initialized = false
//            }
//        }

        // alternative method based on viewmodel and watches if its different than the current user

        saveButton.isEnabled = false
        saveButton.visibility = View.VISIBLE

        viewModel.userName.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank() || it == Session.user?.name) {
                disable(saveButton)
            } else {
                enable(saveButton)
            }
        }

        viewModel.userSurname.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank() || it == Session.user?.surname) {
                disable(saveButton)
            } else {
                enable(saveButton)
            }
        }

        viewModel.userPhone.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank() || it == Session.user?.phone) {
                disable(saveButton)
            } else {
                enable(saveButton)
            }
        }

        saveButton.setOnClickListener {

            lifecycleScope.launch {
                saveButton.isEnabled = false
                try {
                    if(viewModel.updateUser()) {
                        Snackbar.make(view, "Salvataggio effettuato", Snackbar.LENGTH_SHORT).show()
                        disable(saveButton)
                    } else {
                        Snackbar.make(view, "Errore durante il salvataggio", Snackbar.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("InfoFragment", "Error updating user", e)
                    Snackbar.make(view, "Errore durante il salvataggio", Snackbar.LENGTH_SHORT).show()
                } finally {
                    saveButton.isEnabled = true
                }
            }
        }

        editImageButton.setOnClickListener {
            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToImageSelectorFragment())
        }
    }

    private fun enable(saveButton: FloatingActionButton) {
        saveButton.isEnabled = true
    }

    private fun disable(saveButton: FloatingActionButton) {
        saveButton.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        lifecycleScope.cancel("View was destroyed")
    }
}
