package pwm.ar.arpacinema.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentHomeBinding
import pwm.ar.arpacinema.databinding.FragmentProfileMenuBinding

class ProfileMenuFragment : Fragment() {

    private var _binding: FragmentProfileMenuBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileMenuFragment()
    }

    private val viewModel: ProfileMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
}