package pwm.ar.arpacinema.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = InfoFragment()
    }

    private val viewModel: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // hide nav bar
        //requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
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

        nameLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(saveButton)
            }
        }

        surnameLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(saveButton)
            }
        }

        phoneLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(saveButton)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}