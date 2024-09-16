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
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.FragmentInventoryBinding
import pwm.ar.arpacinema.model.Reward
import pwm.ar.arpacinema.parcels.RewardDetailsParcel
import pwm.ar.arpacinema.rewards.OptionsAdapter

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupRecyclerView()
        fetchAndObserveRedeemedItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val decoration = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            isLastItemDecorated = false
        }

        val adapter = OptionsAdapter(
            menuItems = emptyList(), // Initialize with empty list
            showPoints = false
        ) { reward ->
            // Build the parcel
            val parcel = RewardDetailsParcel(
                rewardId = "${Math.random().times(10000).toInt()}",
                rewardCategory = reward.category,
                rewardOption = "${reward.category} : ${ reward.size }"
            )
            val action = CodeFragmentDirections.actionGlobalCodeFragment(parcel)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
            addItemDecoration(decoration)
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun fetchAndObserveRedeemedItems() {
        val userId = Session.userIdInt ?: return
        viewModel.fetchRedeemedItems(userId)

        viewModel.redeemedItems.observe(viewLifecycleOwner) { items ->
            (binding.recyclerView.adapter as? OptionsAdapter)?.updateItems(items)
        }
    }


    private fun setupNavigation() {
        binding.include.viewTitle.text = "I miei premi"
        binding.include.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
