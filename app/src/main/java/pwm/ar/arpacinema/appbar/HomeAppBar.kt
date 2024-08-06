package pwm.ar.arpacinema.appbar

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentHomeAppBarBinding

class HomeAppBar : Fragment() {

    private var _binding: FragmentHomeAppBarBinding? = null
    private val binding
            get() = _binding!!

    companion object {
        fun newInstance() = HomeAppBar()
    }

    private val viewModel: HomeAppBarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAppBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val badgeButton = binding.badge

        badgeButton.setOnClickListener {
            // TODO HEAVY
            val mainNav = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView)
            val navController = mainNav?.findNavController()
            navController?.navigate(R.id.authFragment)

        }
    }
}