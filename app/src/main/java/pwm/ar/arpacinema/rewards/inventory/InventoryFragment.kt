package pwm.ar.arpacinema.rewards.inventory

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
import pwm.ar.arpacinema.databinding.FragmentInventoryBinding
import pwm.ar.arpacinema.model.Reward
import pwm.ar.arpacinema.rewards.OptionsAdapter

class InventoryFragment : Fragment() {

    private var _binding : FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = InventoryFragment()
    }

    private val viewModel: InventoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()

        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val decoration = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            isLastItemDecorated = false
        }
        val adapter = OptionsAdapter(
            listOf(
                Reward("Sconto", "Sconto biglietto (50%)", 500),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
                Reward("Sconto", "Biglietto gratuito (100%)", 1000),
            )
        , showPoints = false) {
            val action = CodeFragmentDirections.actionGlobalCodeFragment()
            findNavController().navigate(action)
        }

        recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
            addItemDecoration(decoration)
            overScrollMode = View.OVER_SCROLL_NEVER
        }




    }

    private fun setupNavigation() {
        binding.include.viewTitle.text = "I miei premi"
        binding.include.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}