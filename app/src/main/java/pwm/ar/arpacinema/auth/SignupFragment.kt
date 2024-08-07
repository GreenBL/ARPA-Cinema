package pwm.ar.arpacinema.auth

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    val sexOptions = listOf("Maschio", "Femmina", "LGBTQ+")

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SignupFragment()
    }

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // animation container transform
        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = false
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            scaleProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.5f, 1f)
        }






        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // handle closing of this
        val closeButton = binding.topBarInclude.closeButton
        closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.topBarInclude.label.text = "Inserisci i tuoi dati"
        val sexChooser = binding.sexField
        val sexAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, sexOptions)
        sexChooser.setAdapter(sexAdapter)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}