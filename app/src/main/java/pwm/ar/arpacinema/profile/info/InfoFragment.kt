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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.FragmentInfoBinding
import pwm.ar.arpacinema.util.PlaceholderDrawable
import pwm.ar.arpacinema.util.TextValidator

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
        val imageview = binding.profileImage

        Glide.with(requireContext())
            .load(Session.userImageURL)
            .placeholder(PlaceholderDrawable.getPlaceholderDrawable())
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.outline_cloud_off_24)
            .into(imageview)

        var initialized = false

        view.post {
            initialized = true
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        // setting up text watchers
        nameLayout.editText?.addTextChangedListener(TextValidator(nameLayout, TextValidator.Companion::isValidName))
        surnameLayout.editText?.addTextChangedListener(TextValidator(surnameLayout, TextValidator.Companion::isValidName))
        phoneLayout.editText?.addTextChangedListener(TextValidator(phoneLayout, TextValidator.Companion::isValidPhone))

        saveButton.isEnabled = false
        saveButton.visibility = View.VISIBLE



        viewModel.userName.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank() || it == Session.user?.name || nameLayout.error != null) {
                disable(saveButton)
            } else {
                enable(saveButton)
            }
        }

        viewModel.userSurname.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank() || it == Session.user?.surname || surnameLayout.error != null) {
                disable(saveButton)
            } else {
                enable(saveButton)
            }
        }

        viewModel.userPhone.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank() || it == Session.user?.phone || phoneLayout.error != null) {
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
                    saveButton.isEnabled = false
                    // trick orribile per nascondere la tastiera
                    nameLayout.isEnabled = false
                    surnameLayout.isEnabled = false
                    phoneLayout.isEnabled = false
                    nameLayout.isEnabled = true
                    surnameLayout.isEnabled = true
                    phoneLayout.isEnabled = true

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
