package pwm.ar.arpacinema.profile.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentHistoryBinding
import pwm.ar.arpacinema.dev.Selection

class HistoryFragment : Fragment() {

    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()

        val recycler = binding.vistaRiciclatrice
        val deco = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            dividerColor = resources.getColor(android.R.color.transparent, null)
            dividerThickness = 64
        }
        val adapter = HistoryAdapter(
            listOf(Selection("Endgame", "20/30/40", "16:30", "A1"),
                Selection("Endgame2", "20/30/40", "16:30", "A1"),
                Selection("Endgame3", "20/30/40", "16:30", "A1"),
                Selection("Endgame4", "20/30/40", "16:30", "A1"),)
        )

        adapter.apply {
            recycler.addItemDecoration(deco)
            recycler.adapter = this
            recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupNavigation() {
        binding.include.viewTitle.text = "Cronologia"
        binding.include.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}