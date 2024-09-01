package pwm.ar.arpacinema.rewards

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.LargeMenuAdapter
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentAccountBinding
import pwm.ar.arpacinema.databinding.FragmentRewardsBinding
import pwm.ar.arpacinema.model.Reward

class RewardsFragment : Fragment() {

    private val topItemList = listOf(MenuItem(R.drawable.outline_fastfood_24, "I miei premi"))
    private val barItemList = listOf(
        MenuItem(R.drawable.popcorn_buckets__dark_background, "Popcorn"),
        MenuItem(R.drawable.tall_drink_cups__dark_background, "Bibite"),
        MenuItem(R.drawable.popcorn_buckets_and_then_some_drink_cups_in_front_, "Combo"))
    private val discountsList = listOf(
        Reward("Sconto", "Sconto biglietto (50%)", 500),
        Reward("Sconto", "Biglietto gratuito (100%)", 1000))

    private var _binding: FragmentRewardsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RewardsFragment()
    }

    private val viewModel: RewardsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include.viewTitle.text = "Premi"

        val topMenuRV = binding.singleButtonMenu // i know... i know...
        val barMenuRV = binding.barrewards
        val discountsRV = binding.discountMenuHostile

        val topMenuAdapter = MenuAdapter(topItemList) {
            // onclicklistener
        }
        val barMenuAdapter = LargeMenuAdapter(barItemList) { menuItem ->

            var selection = "Ni1"

            when (menuItem.label) {
                "Popcorn" -> {
                    selection = menuItem.label
                }
                "Bibite" -> {
                    selection = menuItem.label
                    }
                "Combo" -> {
                    selection = menuItem.label
                }
            }

            val action = RewardsFragmentDirections.actionRewardsFragmentToOptionsFragment(selection)

            findNavController().navigate(action)

        }
        val discountsAdapter = OptionsAdapter(discountsList) {
            // onclicklistener
        }

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        val decorator = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            dividerThickness = 32
            dividerColor = 0x00FFFFFF
        }

        barMenuRV.apply {
            adapter = barMenuAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(decorator)
        }

        topMenuRV.apply {
            adapter = topMenuAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
        }

        discountsRV.apply {
            adapter = discountsAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}