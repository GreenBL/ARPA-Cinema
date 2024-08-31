package pwm.ar.arpacinema.rewards

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment() {

    private var _binding : FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = OptionsFragment()
    }

    private val viewModel: OptionsViewModel by viewModels()
    private val args : OptionsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = args.type
        val textview = binding.textView34
        textview.text = title

        val optionsMenu = binding.optionsMenu
        val optionsAdapter = MenuAdapter(listOf(MenuItem(R.drawable.baseline_arrow_forward_ios_24, "Opzione", false))) {}

        optionsMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = optionsAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}