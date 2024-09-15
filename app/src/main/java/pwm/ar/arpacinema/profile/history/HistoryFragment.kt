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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val quantity = binding.totalMovies

        val recycler = binding.vistaRiciclatrice
        val deco = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            dividerColor = resources.getColor(android.R.color.transparent, null)
            dividerThickness = 32
        }
        val adapter = HistoryAdapter(mutableListOf())

        adapter.apply {
            recycler.addItemDecoration(deco)
            recycler.adapter = this
            recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.compressedTickets.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                return@observe
            }
            adapter.updateList(it)
            quantity.text = "Totale film: ${it.size}"
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupNavigation() {
        binding.include.viewTitle.text = "Cronologia"
        binding.include.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}